
  CREATE OR REPLACE FORCE VIEW "SYS"."ALL_OBJECTS" ("OWNER", "OBJECT_NAME", "SUBOBJECT_NAME", "OBJECT_ID", "DATA_OBJECT_ID", "OBJECT_TYPE", "CREATED", "LAST_DDL_TIME", "TIMESTAMP", "STATUS", "TEMPORARY", "GENERATED", "SECONDARY", "NAMESPACE", "EDITION_NAME") AS 
  select u.name, o.name, o.subname, o.obj#, o.dataobj#,
       decode(o.type#, 0, 'NEXT OBJECT', 1, 'INDEX', 2, 'TABLE', 3, 'CLUSTER',
                      4, 'VIEW', 5, 'SYNONYM', 6, 'SEQUENCE',
                      7, 'PROCEDURE', 8, 'FUNCTION', 9, 'PACKAGE',
                      11, 'PACKAGE BODY', 12, 'TRIGGER',
                      13, 'TYPE', 14, 'TYPE BODY',
                      19, 'TABLE PARTITION', 20, 'INDEX PARTITION', 21, 'LOB',
                      22, 'LIBRARY', 23, 'DIRECTORY', 24, 'QUEUE',
                      28, 'JAVA SOURCE', 29, 'JAVA CLASS', 30, 'JAVA RESOURCE',
                      32, 'INDEXTYPE', 33, 'OPERATOR',
                      34, 'TABLE SUBPARTITION', 35, 'INDEX SUBPARTITION',
                      40, 'LOB PARTITION', 41, 'LOB SUBPARTITION',
                      42, NVL((SELECT 'REWRITE EQUIVALENCE'
                               FROM sum$ s
                               WHERE s.obj#=o.obj#
                                     and bitand(s.xpflags, 8388608) = 8388608),
                              'MATERIALIZED VIEW'),
                      43, 'DIMENSION',
                      44, 'CONTEXT', 46, 'RULE SET', 47, 'RESOURCE PLAN',
                      48, 'CONSUMER GROUP',
                      55, 'XML SCHEMA', 56, 'JAVA DATA',
                      57, 'EDITION', 59, 'RULE',
                      60, 'CAPTURE', 61, 'APPLY',
                      62, 'EVALUATION CONTEXT',
                      66, 'JOB', 67, 'PROGRAM', 68, 'JOB CLASS', 69, 'WINDOW',
                      72, 'SCHEDULER GROUP', 74, 'SCHEDULE', 79, 'CHAIN',
                      81, 'FILE GROUP', 82, 'MINING MODEL', 87, 'ASSEMBLY',
                      90, 'CREDENTIAL', 92, 'CUBE DIMENSION', 93, 'CUBE',
                      94, 'MEASURE FOLDER', 95, 'CUBE BUILD PROCESS',
                      100, 'FILE WATCHER', 101, 'DESTINATION',
                     'UNDEFINED'),
       o.ctime, o.mtime,
       to_char(o.stime, 'YYYY-MM-DD:HH24:MI:SS'),
       decode(o.status, 0, 'N/A', 1, 'VALID', 'INVALID'),
       decode(bitand(o.flags, 2), 0, 'N', 2, 'Y', 'N'),
       decode(bitand(o.flags, 4), 0, 'N', 4, 'Y', 'N'),
       decode(bitand(o.flags, 16), 0, 'N', 16, 'Y', 'N'),
       o.namespace,
       o.defining_edition
from sys."_CURRENT_EDITION_OBJ" o, sys.user$ u
where o.owner# = u.user#
  and o.linkname is null
  and (o.type# not in (1  /* INDEX - handled below */,
                      10 /* NON-EXISTENT */)
       or
       (o.type# = 1 and 1 = (select 1
                             from sys.ind$ i
                            where i.obj# = o.obj#
                              and i.type# in (1, 2, 3, 4, 6, 7, 9))))
  and o.name != '_NEXT_OBJECT'
  and o.name != '_default_auditing_options_'
  and bitand(o.flags, 128) = 0
  and
  (
    o.owner# in (userenv('SCHEMAID'), 1 /* PUBLIC */)
    or
    (
      /* non-procedural objects */
      o.type# not in (7, 8, 9, 11, 12, 13, 14, 28, 29, 30, 56, 93)
      and
      o.obj# in (select obj# from sys.objauth$
                 where grantee# in (select kzsrorol from x$kzsro)
                   and privilege# in (3 /* DELETE */,   6 /* INSERT */,
                                      7 /* LOCK */,     9 /* SELECT */,
                                      10 /* UPDATE */, 12 /* EXECUTE */,
                                      11 /* USAGE */,  16 /* CREATE */,
                                      17 /* READ */,   18 /* WRITE  */ ))
    )
    or
    (
       o.type# in (7, 8, 9, 28, 29, 30, 56) /* prc, fcn, pkg */
       and
       (
         exists (select null from sys.objauth$ oa
                  where oa.obj# = o.obj#
                    and oa.grantee# in (select kzsrorol from x$kzsro)
                    and oa.privilege# in (12 /* EXECUTE */, 26 /* DEBUG */))
         or
         exists (select null from v$enabledprivs
                 where priv_number in (
                                        -144 /* EXECUTE ANY PROCEDURE */,
                                        -141 /* CREATE ANY PROCEDURE */,
                                        -241 /* DEBUG ANY PROCEDURE */
                                      )
                )
       )
    )
    or
    (
       o.type# in (19) /* partitioned table objects */
       and
       exists (select bo# from tabpart$ where obj# = o.obj# and
               bo# in  (select obj# from sys.objauth$
                where grantee# in (select kzsrorol from x$kzsro)
                  and privilege# in (9 /* SELECT */ ))
              )
    )
    or
    (
       o.type# in (12) /* trigger */
       and
       (
         exists (select null from sys.trigger$ t, sys.objauth$ oa
                  where bitand(t.property, 24) = 0
                    and t.obj# = o.obj#
                    and oa.obj# = t.baseobject
                    and oa.grantee# in (select kzsrorol from x$kzsro)
                    and oa.privilege# = 26 /* DEBUG */)
         or
         exists (select null from v$enabledprivs
                 where priv_number in (
                                        -152 /* CREATE ANY TRIGGER */,
                                        -241 /* DEBUG ANY PROCEDURE */
                                      )
              )
       )
    )
    or
    (
       o.type# = 11 /* pkg body */
       and
       (
         exists (select null
                   from sys."_ACTUAL_EDITION_OBJ" specobj, sys.dependency$ dep,
                        sys.objauth$ oa
                  where specobj.owner# = o.owner#
                    and specobj.name = o.name
                    and specobj.type# = 9 /* pkg */
                    and dep.d_obj# = o.obj# and dep.p_obj# = specobj.obj#
                    and oa.obj# = specobj.obj#
                    and oa.grantee# in (select kzsrorol from x$kzsro)
                    and oa.privilege# = 26 /* DEBUG */)
         or
         exists (select null from v$enabledprivs
                 where priv_number in (
                                        -141 /* CREATE ANY PROCEDURE */,
                                        -241 /* DEBUG ANY PROCEDURE */
                                      )
                )
       )
    )
    or
    (
       o.type# in (22) /* library */
       and
       exists (select null from v$enabledprivs
               where priv_number in (
                                      -189 /* CREATE ANY LIBRARY */,
                                      -190 /* ALTER ANY LIBRARY */,
                                      -191 /* DROP ANY LIBRARY */,
                                      -192 /* EXECUTE ANY LIBRARY */
                                    )
              )
    )
    or
    (
       /* index, table, view, synonym, table partn, indx partn, */
       /* table subpartn, index subpartn, cluster               */
       o.type# in (1, 2, 3, 4, 5, 19, 20, 34, 35)
       and
       exists (select null from v$enabledprivs
               where priv_number in (-45 /* LOCK ANY TABLE */,
                                     -47 /* SELECT ANY TABLE */,
                                     -48 /* INSERT ANY TABLE */,
                                     -49 /* UPDATE ANY TABLE */,
                                     -50 /* DELETE ANY TABLE */)
               )
    )
    or
    ( o.type# = 6 /* sequence */
      and
      exists (select null from v$enabledprivs
              where priv_number = -109 /* SELECT ANY SEQUENCE */)
    )
    or
    ( o.type# = 13 /* type */
      and
      (
        exists (select null from sys.objauth$ oa
                 where oa.obj# = o.obj#
                   and oa.grantee# in (select kzsrorol from x$kzsro)
                   and oa.privilege# in (12 /* EXECUTE */, 26 /* DEBUG */))
        or
        exists (select null from v$enabledprivs
                where priv_number in (-184 /* EXECUTE ANY TYPE */,
                                      -181 /* CREATE ANY TYPE */,
                                      -241 /* DEBUG ANY PROCEDURE */))
      )
    )
    or
    (
      o.type# = 14 /* type body */
      and
      (
        exists (select null
                  from sys."_ACTUAL_EDITION_OBJ" specobj, sys.dependency$ dep,
                       sys.objauth$ oa
                 where specobj.owner# = o.owner#
                   and specobj.name = o.name
                   and specobj.type# = 13 /* type */
                   and dep.d_obj# = o.obj# and dep.p_obj# = specobj.obj#
                   and oa.obj# = specobj.obj#
                   and oa.grantee# in (select kzsrorol from x$kzsro)
                   and oa.privilege# = 26 /* DEBUG */)
        or
        exists (select null from v$enabledprivs
                where priv_number in (
                                       -181 /* CREATE ANY TYPE */,
                                       -241 /* DEBUG ANY PROCEDURE */
                                     )
               )
      )
    )
    or
    (
       o.type# = 23 /* directory */
       and
       exists (select null from v$enabledprivs
               where priv_number in (
                                      -177 /* CREATE ANY DIRECTORY */,
                                      -178 /* DROP ANY DIRECTORY */
                                    )
              )
    )
    or
    (
       o.type# = 42 /* summary jjf table privs have to change to summary */
       and
         exists (select null from v$enabledprivs
                 where priv_number in (-45 /* LOCK ANY TABLE */,
                                       -47 /* SELECT ANY TABLE */,
                                       -48 /* INSERT ANY TABLE */,
                                       -49 /* UPDATE ANY TABLE */,
                                       -50 /* DELETE ANY TABLE */)
                 )
    )
    or
    (
      o.type# = 32   /* indextype */
      and
      exists (select null from v$enabledprivs
               where priv_number in (
                                      -205  /* CREATE INDEXTYPE */ ,
                                      -206  /* CREATE ANY INDEXTYPE */ ,
                                      -207  /* ALTER ANY INDEXTYPE */ ,
                                      -208  /* DROP ANY INDEXTYPE */
                                    )
             )
    )
    or
    (
      o.type# = 33   /* operator */
      and
      exists (select null from v$enabledprivs
               where priv_number in (
                                      -200  /* CREATE OPERATOR */ ,
                                      -201  /* CREATE ANY OPERATOR */ ,
                                      -202  /* ALTER ANY OPERATOR */ ,
                                      -203  /* DROP ANY OPERATOR */ ,
                                      -204  /* EXECUTE OPERATOR */
                                    )
             )
    )
    or
    (
      o.type# = 44   /* context */
      and
      exists (select null from v$enabledprivs
               where priv_number in (
                                      -222  /* CREATE ANY CONTEXT */,
                                      -223  /* DROP ANY CONTEXT */
                                    )
             )
    )
    or
    (
      o.type# = 48  /* resource consumer group */
      and
      exists (select null from v$enabledprivs
              where priv_number in (12)  /* switch consumer group privilege */
             )
    )
    or
    (
      o.type# = 46 /* rule set */
      and
      exists (select null from v$enabledprivs
               where priv_number in (
                                      -251, /* create any rule set */
                                      -252, /* alter any rule set */
                                      -253, /* drop any rule set */
                                      -254  /* execute any rule set */
                                    )
             )
    )
    or
    (
      o.type# = 55 /* XML schema */
      and
      1 = (select /*+ NO_MERGE */ xml_schema_name_present.is_schema_present(o.name, u2.id2) id1 from (select /*+ NO_MERGE */ userenv('SCHEMAID') id2 from dual) u2)
      /* we need a sub-query instead of the directy invoking
       * xml_schema_name_present, because inside a view even the function
       * arguments are evaluated as definers rights.
       */
    )
    or
    (
      o.type# = 59 /* rule */
      and
      exists (select null from v$enabledprivs
               where priv_number in (
                                      -258, /* create any rule */
                                      -259, /* alter any rule */
                                      -260, /* drop any rule */
                                      -261  /* execute any rule */
                                    )
             )
    )
    or
    (
      o.type# = 62 /* evaluation context */
      and
      exists (select null from v$enabledprivs
               where priv_number in (
                                      -246, /* create any evaluation context */
                                      -247, /* alter any evaluation context */
                                      -248, /* drop any evaluation context */
                                      -249 /* execute any evaluation context */
                                    )
             )
    )
    or
    (
      o.type# IN (66, 100)  /* scheduler job or file watcher */
      and
      exists (select null from v$enabledprivs
               where priv_number = -265 /* create any job */
             )
    )
    or
    (
      o.type# IN (67, 79) /* scheduler program or chain */
      and
      exists (select null from v$enabledprivs
               where priv_number in (
                                      -265, /* create any job */
                                      -266 /* execute any program */
                                    )
             )
    )
    or
    (
      o.type# = 68 /* scheduler job class */
      and
      exists (select null from v$enabledprivs
               where priv_number in (
                                      -268, /* manage scheduler */
                                      -267 /* execute any class */
                                    )
             )
    )
    or (o.type# in (69, 72, 74, 101))
    /* scheduler windows, scheduler groups, schedules and destinations */
    /* no privileges are needed to view these objects */
    or
    (
      o.type# = 81 /* file group */
      and
      exists (select null from v$enabledprivs
               where priv_number in (
                                       -277, /* manage any file group */
                                       -278  /* read any file group */
                                    )
             )
    )
    or
    (
      o.type# = 57 /* edition */
    )
    or
    (
      o.type# = 82 /* mining model */
      and
      exists (select null from v$enabledprivs
               where priv_number in (
                                       -292, /* drop any mining model */
                                       -293, /* select any mining model */
                                       -294  /* alter any mining model */
                                    )
             )
    )
    or
    (
       o.type# in (87) /* assembly */
       and
       exists (select null from v$enabledprivs
               where priv_number in (
                                      -282 /* CREATE ANY ASSEMBLY */,
                                      -283 /* ALTER ANY ASSEMBLY */,
                                      -284 /* DROP ANY ASSEMBLY */,
                                      -285 /* EXECUTE ANY ASSEMBLY */
                                    )
              )
    )
    or
    (
      o.type# = 92 /* cube dimension */
      and
      exists (select null from v$enabledprivs
              where priv_number in (
                                      -302, /* ALTER ANY PRIMARY DIMENSION */
                                      -303, /* CREATE ANY PRIMARY DIMENSION */
                                      -304, /* DELETE ANY PRIMARY DIMENSION */
                                      -305, /* DROP ANY PRIMARY DIMENSION */
                                      -306, /* INSERT ANY PRIMARY DIMENSION */
                                      -307  /* SELECT ANY PRIMARY DIMENSION */
                                   )
             )
    )
    or
    (
      o.type# = 93 /* cube */
      and
      (o.obj# in
            ( select obj#  /* directly granted privileges */
              from sys.objauth$
              where grantee# in ( select kzsrorol from x$kzsro )
            )
       or
       (
        exists (select null from v$enabledprivs
                where priv_number in (
                                        -309, /* ALTER ANY CUBE */
                                        -310, /* CREATE ANY CUBE */
                                        -311, /* DROP ANY CUBE */
                                        -312, /* SELECT ANY CUBE */
                                        -313  /* UPDATE ANY CUBE */
                                     )
               )
       )
      )
      and  /* require access to all Dimensions of the Cube */
      ( 1 = ( SELECT decode(have_all_dim_access, null, 1, have_all_dim_access)
              FROM
                ( SELECT
                    obj#,
                    MIN(have_dim_access) have_all_dim_access
                  FROM
                    ( SELECT
                        c.obj# obj#,
                        ( CASE
                          WHEN
                            ( do.owner# in ( userenv('SCHEMAID'), 1 )  /* public objects */
                              or do.obj# in
                              ( select obj#  /* directly granted privileges */
                                from sys.objauth$
                                where grantee# in ( select kzsrorol from x$kzsro )
                              )
                              or  /* user has system privileges */
                              ( exists ( select null from v$enabledprivs
                                         where priv_number in (
                                                                 -302, /* ALTER ANY PRIMARY DIMENSION */
                                                                 -303, /* CREATE ANY PRIMARY DIMENSION */
                                                                 -304, /* DELETE ANY PRIMARY DIMENSION */
                                                                 -305, /* DROP ANY PRIMARY DIMENSION */
                                                                 -306, /* INSERT ANY PRIMARY DIMENSION */
                                                                 -307  /* SELECT ANY PRIMARY DIMENSION */
                                                              )
                                       )
                              )
                            )
                          THEN 1
                          ELSE 0
                          END ) have_dim_access
                      FROM
                        olap_cubes$ c,
                        olap_dimensionality$ diml,
                        olap_cube_dimensions$ dim,
                        obj$ do
                      WHERE
                        do.obj# = dim.obj#
                        AND diml.dimensioned_object_type = 1 /* CUBE */
                        AND diml.dimensioned_object_id = c.obj#
                        AND diml.dimension_type = 11 /* DIMENSION */
                        AND diml.dimension_id = do.obj#
                    )
                  GROUP BY obj# ) da
              WHERE
                o.obj#=da.obj#(+)
            )
      )
    )
    or
    (
      o.type# = 94 /* measure folder */
      and
      exists (select null from v$enabledprivs
              where priv_number in (
                                      -315, /* CREATE ANY MEASURE FOLDER */
                                      -316, /* DELETE ANY MEASURE FOLDER */
                                      -317, /* DROP ANY MEASURE FOLDER */
                                      -318  /* INSERT ANY MEASURE FOLDER */
                                   )
             )
    )
    or
    (
      o.type# = 95 /* cube build process */
      and
      exists (select null from v$enabledprivs
              where priv_number in (
                                      -320, /* CREATE ANY BUILD PROCESS */
                                      -321, /* DROP ANY BUILD PROCESS */
                                      -322  /* UPDATE ANY BUILD PROCESS */
                                   )
             )
    )
  );

   COMMENT ON COLUMN "SYS"."ALL_OBJECTS"."OWNER" IS 'Username of the owner of the object';
   COMMENT ON COLUMN "SYS"."ALL_OBJECTS"."OBJECT_NAME" IS 'Name of the object';
   COMMENT ON COLUMN "SYS"."ALL_OBJECTS"."SUBOBJECT_NAME" IS 'Name of the sub-object (for example, partititon)';
   COMMENT ON COLUMN "SYS"."ALL_OBJECTS"."OBJECT_ID" IS 'Object number of the object';
   COMMENT ON COLUMN "SYS"."ALL_OBJECTS"."DATA_OBJECT_ID" IS 'Object number of the segment which contains the object';
   COMMENT ON COLUMN "SYS"."ALL_OBJECTS"."OBJECT_TYPE" IS 'Type of the object';
   COMMENT ON COLUMN "SYS"."ALL_OBJECTS"."CREATED" IS 'Timestamp for the creation of the object';
   COMMENT ON COLUMN "SYS"."ALL_OBJECTS"."LAST_DDL_TIME" IS 'Timestamp for the last DDL change (including GRANT and REVOKE) to the object';
   COMMENT ON COLUMN "SYS"."ALL_OBJECTS"."TIMESTAMP" IS 'Timestamp for the specification of the object';
   COMMENT ON COLUMN "SYS"."ALL_OBJECTS"."STATUS" IS 'Status of the object';
   COMMENT ON COLUMN "SYS"."ALL_OBJECTS"."TEMPORARY" IS 'Can the current session only see data that it placed in this object itself?';
   COMMENT ON COLUMN "SYS"."ALL_OBJECTS"."GENERATED" IS 'Was the name of this object system generated?';
   COMMENT ON COLUMN "SYS"."ALL_OBJECTS"."SECONDARY" IS 'Is this a secondary object created as part of icreate for domain indexes?';
   COMMENT ON COLUMN "SYS"."ALL_OBJECTS"."NAMESPACE" IS 'Namespace for the object';
   COMMENT ON COLUMN "SYS"."ALL_OBJECTS"."EDITION_NAME" IS 'Name of the edition in which the object is actual';
   COMMENT ON TABLE "SYS"."ALL_OBJECTS"  IS 'Objects accessible to the user';
