
  CREATE OR REPLACE FORCE VIEW "SYS"."ALL_TABLES" ("OWNER", "TABLE_NAME", "TABLESPACE_NAME", "CLUSTER_NAME", "IOT_NAME", "STATUS", "PCT_FREE", "PCT_USED", "INI_TRANS", "MAX_TRANS", "INITIAL_EXTENT", "NEXT_EXTENT", "MIN_EXTENTS", "MAX_EXTENTS", "PCT_INCREASE", "FREELISTS", "FREELIST_GROUPS", "LOGGING", "BACKED_UP", "NUM_ROWS", "BLOCKS", "EMPTY_BLOCKS", "AVG_SPACE", "CHAIN_CNT", "AVG_ROW_LEN", "AVG_SPACE_FREELIST_BLOCKS", "NUM_FREELIST_BLOCKS", "DEGREE", "INSTANCES", "CACHE", "TABLE_LOCK", "SAMPLE_SIZE", "LAST_ANALYZED", "PARTITIONED", "IOT_TYPE", "TEMPORARY", "SECONDARY", "NESTED", "BUFFER_POOL", "FLASH_CACHE", "CELL_FLASH_CACHE", "ROW_MOVEMENT", "GLOBAL_STATS", "USER_STATS", "DURATION", "SKIP_CORRUPT", "MONITORING", "CLUSTER_OWNER", "DEPENDENCIES", "COMPRESSION", "COMPRESS_FOR", "DROPPED", "READ_ONLY", "SEGMENT_CREATED", "RESULT_CACHE") AS 
  select u.name, o.name,
       decode(bitand(t.property,2151678048), 0, ts.name,
              decode(t.ts#, 0, null, ts.name)),
       decode(bitand(t.property, 1024), 0, null, co.name),
       decode((bitand(t.property, 512)+bitand(t.flags, 536870912)),
              0, null, co.name),
       decode(bitand(t.trigflag, 1073741824), 1073741824, 'UNUSABLE', 'VALID'),
       decode(bitand(t.property, 32+64), 0, mod(t.pctfree$, 100), 64, 0, null),
       decode(bitand(ts.flags, 32), 32, to_number(NULL),
          decode(bitand(t.property, 32+64), 0, t.pctused$, 64, 0, null)),
       decode(bitand(t.property, 32), 0, t.initrans, null),
       decode(bitand(t.property, 32), 0, t.maxtrans, null),
       decode(bitand(t.property, 17179869184), 17179869184,
                     ds.initial_stg * ts.blocksize,
                     s.iniexts * ts.blocksize),
       decode(bitand(t.property, 17179869184), 17179869184,
              ds.next_stg * ts.blocksize,
              s.extsize * ts.blocksize),
       decode(bitand(t.property, 17179869184), 17179869184,
              ds.minext_stg, s.minexts),
       decode(bitand(t.property, 17179869184), 17179869184,
              ds.maxext_stg, s.maxexts),
       decode(bitand(ts.flags, 3), 1, to_number(NULL),
              decode(bitand(t.property, 17179869184), 17179869184,
                            ds.pctinc_stg, s.extpct)),
       decode(bitand(ts.flags, 32), 32, to_number(NULL),
         decode(bitand(o.flags, 2), 2, 1,
                decode(bitand(t.property, 17179869184), 17179869184,
                       decode(ds.frlins_stg, 0, 1, ds.frlins_stg),
                       decode(s.lists, 0, 1, s.lists)))),
       decode(bitand(ts.flags, 32), 32, to_number(NULL),
         decode(bitand(o.flags, 2), 2, 1,
                decode(bitand(t.property, 17179869184), 17179869184,
                       decode(ds.maxins_stg, 0, 1, ds.maxins_stg),
                       decode(s.groups, 0, 1, s.groups)))),
       decode(bitand(t.property, 32+64), 0,
                decode(bitand(t.flags, 32), 0, 'YES', 'NO'), null),
       decode(bitand(t.flags,1), 0, 'Y', 1, 'N', '?'),
       t.rowcnt,
       decode(bitand(t.property, 64), 0, t.blkcnt, null),
       decode(bitand(t.property, 64), 0, t.empcnt, null),
       decode(bitand(t.property, 64), 0, t.avgspc, null),
       t.chncnt, t.avgrln, t.avgspc_flb,
       decode(bitand(t.property, 64), 0, t.flbcnt, null),
       lpad(decode(t.degree, 32767, 'DEFAULT', nvl(t.degree,1)),10),
       lpad(decode(t.instances, 32767, 'DEFAULT', nvl(t.instances,1)),10),
       lpad(decode(bitand(t.flags, 8), 8, 'Y', 'N'),5),
       decode(bitand(t.flags, 6), 0, 'ENABLED', 'DISABLED'),
       t.samplesize, t.analyzetime,
       decode(bitand(t.property, 32), 32, 'YES', 'NO'),
       decode(bitand(t.property, 64), 64, 'IOT',
               decode(bitand(t.property, 512), 512, 'IOT_OVERFLOW',
               decode(bitand(t.flags, 536870912), 536870912, 'IOT_MAPPING', null))),
       decode(bitand(o.flags, 2), 0, 'N', 2, 'Y', 'N'),
       decode(bitand(o.flags, 16), 0, 'N', 16, 'Y', 'N'),
       decode(bitand(t.property, 8192), 8192, 'YES',
              decode(bitand(t.property, 1), 0, 'NO', 'YES')),
       decode(bitand(o.flags, 2), 2, 'DEFAULT',
              decode(bitand(decode(bitand(t.property, 17179869184), 17179869184,
                            ds.bfp_stg, s.cachehint), 3),
                            1, 'KEEP', 2, 'RECYCLE', 'DEFAULT')),
       decode(bitand(o.flags, 2), 2, 'DEFAULT',
              decode(bitand(decode(bitand(t.property, 17179869184), 17179869184,
                            ds.bfp_stg, s.cachehint), 12)/4,
                            1, 'KEEP', 2, 'NONE', 'DEFAULT')),
       decode(bitand(o.flags, 2), 2, 'DEFAULT',
              decode(bitand(decode(bitand(t.property, 17179869184), 17179869184,
                            ds.bfp_stg, s.cachehint), 48)/16,
                            1, 'KEEP', 2, 'NONE', 'DEFAULT')),
       decode(bitand(t.flags, 131072), 131072, 'ENABLED', 'DISABLED'),
       decode(bitand(t.flags, 512), 0, 'NO', 'YES'),
       decode(bitand(t.flags, 256), 0, 'NO', 'YES'),
       decode(bitand(o.flags, 2), 0, NULL,
          decode(bitand(t.property, 8388608), 8388608,
                 'SYS$SESSION', 'SYS$TRANSACTION')),
       decode(bitand(t.flags, 1024), 1024, 'ENABLED', 'DISABLED'),
       decode(bitand(o.flags, 2), 2, 'NO',
           decode(bitand(t.property, 2147483648), 2147483648, 'NO',
              decode(ksppcv.ksppstvl, 'TRUE', 'YES', 'NO'))),
       decode(bitand(t.property, 1024), 0, null, cu.name),
       decode(bitand(t.flags, 8388608), 8388608, 'ENABLED', 'DISABLED'),
       case when (bitand(t.property, 32) = 32) then
         null
       when (bitand(t.property, 17179869184) = 17179869184) then
          decode(bitand(ds.flags_stg, 4), 4, 'ENABLED', 'DISABLED')
       else
         decode(bitand(s.spare1, 2048), 2048, 'ENABLED', 'DISABLED')
       end,
       case when (bitand(t.property, 32) = 32) then
         null
       when (bitand(t.property, 17179869184) = 17179869184) then
          decode(bitand(ds.flags_stg, 4), 4,
          case when bitand(ds.cmpflag_stg, 3) = 1 then 'BASIC'
               when bitand(ds.cmpflag_stg, 3) = 2 then 'OLTP'
               else decode(ds.cmplvl_stg, 1, 'QUERY LOW',
                                          2, 'QUERY HIGH',
                                          3, 'ARCHIVE LOW',
                                             'ARCHIVE HIGH') end,
               null)
       else
         decode(bitand(s.spare1, 2048), 0, null,
         case when bitand(s.spare1, 16777216) = 16777216
                   then 'OLTP'
              when bitand(s.spare1, 100663296) = 33554432  -- 0x2000000
                   then 'QUERY LOW'
              when bitand(s.spare1, 100663296) = 67108864  -- 0x4000000
                   then 'QUERY HIGH'
              when bitand(s.spare1, 100663296) = 100663296 -- 0x2000000+0x4000000
                   then 'ARCHIVE LOW'
              when bitand(s.spare1, 134217728) = 134217728 -- 0x8000000
                   then 'ARCHIVE HIGH'
              else 'BASIC' end)
       end,
       decode(bitand(o.flags, 128), 128, 'YES', 'NO'),
       decode(bitand(t.trigflag, 2097152), 2097152, 'YES', 'NO'),
       decode(bitand(t.property, 17179869184), 17179869184, 'NO',
              decode(bitand(t.property, 32), 32, 'N/A', 'YES')),
       decode(bitand(t.property,16492674416640),2199023255552,'FORCE',
                 4398046511104,'MANUAL','DEFAULT')
from sys.user$ u, sys.ts$ ts, sys.seg$ s, sys.obj$ co, sys.tab$ t, sys.obj$ o,
     sys.obj$ cx, sys.user$ cu, x$ksppcv ksppcv, x$ksppi ksppi,
     sys.deferred_stg$ ds
where o.owner# = u.user#
  and o.obj# = t.obj#
  and bitand(t.property, 1) = 0
  and bitand(o.flags, 128) = 0
  and t.bobj# = co.obj# (+)
  and t.ts# = ts.ts#
  and t.obj# = ds.obj# (+)
  and t.file# = s.file# (+)
  and t.block# = s.block# (+)
  and t.ts# = s.ts# (+)
  and (o.owner# = userenv('SCHEMAID')
       or o.obj# in
            (select oa.obj#
             from sys.objauth$ oa
             where grantee# in ( select kzsrorol
                                 from x$kzsro
                               )
            )
       or /* user has system privileges */
         exists (select null from v$enabledprivs
                 where priv_number in (-45 /* LOCK ANY TABLE */,
                                       -47 /* SELECT ANY TABLE */,
                                       -48 /* INSERT ANY TABLE */,
                                       -49 /* UPDATE ANY TABLE */,
                                       -50 /* DELETE ANY TABLE */)
                 )
      )
  and t.dataobj# = cx.obj# (+)
  and cx.owner# = cu.user# (+)
  and ksppi.indx = ksppcv.indx
  and ksppi.ksppinm = '_dml_monitoring_enabled';

   COMMENT ON COLUMN "SYS"."ALL_TABLES"."OWNER" IS 'Owner of the table';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."TABLE_NAME" IS 'Name of the table';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."TABLESPACE_NAME" IS 'Name of the tablespace containing the table';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."CLUSTER_NAME" IS 'Name of the cluster, if any, to which the table belongs';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."IOT_NAME" IS 'Name of the index-only table, if any, to which the overflow or mapping table entry belongs';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."STATUS" IS 'Status of the table will be UNUSABLE if a previous DROP TABLE operation failed,
VALID otherwise';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."PCT_FREE" IS 'Minimum percentage of free space in a block';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."PCT_USED" IS 'Minimum percentage of used space in a block';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."INI_TRANS" IS 'Initial number of transactions';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."MAX_TRANS" IS 'Maximum number of transactions';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."INITIAL_EXTENT" IS 'Size of the initial extent in bytes';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."NEXT_EXTENT" IS 'Size of secondary extents in bytes';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."MIN_EXTENTS" IS 'Minimum number of extents allowed in the segment';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."MAX_EXTENTS" IS 'Maximum number of extents allowed in the segment';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."PCT_INCREASE" IS 'Percentage increase in extent size';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."FREELISTS" IS 'Number of process freelists allocated in this segment';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."FREELIST_GROUPS" IS 'Number of freelist groups allocated in this segment';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."LOGGING" IS 'Logging attribute';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."BACKED_UP" IS 'Has table been backed up since last modification?';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."NUM_ROWS" IS 'The number of rows in the table';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."BLOCKS" IS 'The number of used blocks in the table';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."EMPTY_BLOCKS" IS 'The number of empty (never used) blocks in the table';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."AVG_SPACE" IS 'The average available free space in the table';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."CHAIN_CNT" IS 'The number of chained rows in the table';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."AVG_ROW_LEN" IS 'The average row length, including row overhead';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."AVG_SPACE_FREELIST_BLOCKS" IS 'The average freespace of all blocks on a freelist';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."NUM_FREELIST_BLOCKS" IS 'The number of blocks on the freelist';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."DEGREE" IS 'The number of threads per instance for scanning the table';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."INSTANCES" IS 'The number of instances across which the table is to be scanned';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."CACHE" IS 'Whether the table is to be cached in the buffer cache';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."TABLE_LOCK" IS 'Whether table locking is enabled or disabled';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."SAMPLE_SIZE" IS 'The sample size used in analyzing this table';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."LAST_ANALYZED" IS 'The date of the most recent time this table was analyzed';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."PARTITIONED" IS 'Is this table partitioned? YES or NO';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."IOT_TYPE" IS 'If index-only table, then IOT_TYPE is IOT or IOT_OVERFLOW or IOT_MAPPING else NULL';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."TEMPORARY" IS 'Can the current session only see data that it place in this object itself?';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."SECONDARY" IS 'Is this table object created as part of icreate for domain indexes?';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."NESTED" IS 'Is the table a nested table?';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."BUFFER_POOL" IS 'The default buffer pool to be used for table blocks';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."FLASH_CACHE" IS 'The default flash cache hint to be used for table blocks';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."CELL_FLASH_CACHE" IS 'The default cell flash cache hint to be used for table blocks';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."ROW_MOVEMENT" IS 'Whether partitioned row movement is enabled or disabled';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."GLOBAL_STATS" IS 'Are the statistics calculated without merging underlying partitions?';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."USER_STATS" IS 'Were the statistics entered directly by the user?';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."DURATION" IS 'If temporary table, then duration is sys$session or sys$transaction else NULL';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."SKIP_CORRUPT" IS 'Whether skip corrupt blocks is enabled or disabled';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."MONITORING" IS 'Should we keep track of the amount of modification?';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."CLUSTER_OWNER" IS 'Owner of the cluster, if any, to which the table belongs';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."DEPENDENCIES" IS 'Should we keep track of row level dependencies?';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."COMPRESSION" IS 'Whether table compression is enabled or not';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."COMPRESS_FOR" IS 'Compress what kind of operations';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."DROPPED" IS 'Whether table is dropped and is in Recycle Bin';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."READ_ONLY" IS 'Whether table is read only or not';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."SEGMENT_CREATED" IS 'Whether the table segment is created or not';
   COMMENT ON COLUMN "SYS"."ALL_TABLES"."RESULT_CACHE" IS 'The result cache mode annotation for the table';
   COMMENT ON TABLE "SYS"."ALL_TABLES"  IS 'Description of relational tables accessible to the user';
