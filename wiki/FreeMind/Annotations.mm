<map version="1.0.1">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1412945098107" ID="ID_563609846" MODIFIED="1412945107338" TEXT="Annotations">
<node CREATED="1412945542249" ID="ID_1678864136" MODIFIED="1413991588919" POSITION="right" TEXT="Docs">
<node CREATED="1413991549412" ID="ID_1774884784" MODIFIED="1413991564142" TEXT="Source -&gt; Browse -&gt; svn -&gt; doc"/>
<node CREATED="1413991590109" MODIFIED="1413991590109" TEXT="AnnotationProcessing_Eclipse - acris - How to setup annotation processors with eclipse - Web framework for building applications, web sites and portals using Google Web Toolkit - Google Project Hosting.htm"/>
<node CREATED="1413991590119" MODIFIED="1413991590119" TEXT="Code Generation using Annotation Processors in the Java language &#x2013; part 2 Annotation Processors dr. macphail&apos;s trance.htm"/>
<node CREATED="1413991590169" MODIFIED="1413991590169" TEXT="Lexer.java"/>
<node CREATED="1413991590169" MODIFIED="1413991590169" TEXT="Writing a Lexer in Java 1.7 using Regex Named Capturing Groups.htm"/>
</node>
<node CREATED="1413180299383" ID="ID_1583330265" MODIFIED="1414163224705" POSITION="left" TEXT="Declarations">
<node CREATED="1413991216153" ID="ID_1492722610" MODIFIED="1413991225893" TEXT="Project AcaciaAnn"/>
<node CREATED="1413991421575" ID="ID_1448579381" MODIFIED="1413991421575" TEXT="AnnState.java&#xa;AnnToken.java&#xa;AnnTokens.java&#xa;AnnUnknown.java&#xa;AnnLexer.java&#xa;AnnStartState.java"/>
</node>
<node CREATED="1413180366723" ID="ID_1703474036" MODIFIED="1413180369223" POSITION="right" TEXT="Lexer">
<node CREATED="1413180330243" ID="ID_1910195313" MODIFIED="1414163212791" STYLE="fork" TEXT="Interfaces">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Classes to call and process Lexer
    </p>
  </body>
</html>
</richcontent>
<node CREATED="1414162076099" ID="ID_1603565678" MODIFIED="1414163482973" TEXT="Project Accacia Package lexer"/>
<node CREATED="1414162124169" ID="ID_1857630232" MODIFIED="1414162129159" TEXT="Lexer.java"/>
<node CREATED="1414162135989" ID="ID_1565920620" MODIFIED="1414162140389" TEXT="Token.java"/>
<node CREATED="1414162262011" ID="ID_1978866510" MODIFIED="1414162322441" TEXT="Status.java"/>
<node CREATED="1414162224000" ID="ID_1967016352" MODIFIED="1414162228950" TEXT="StateDesc.java"/>
<node CREATED="1414162309701" ID="ID_663939264" MODIFIED="1414162317121" TEXT="StateInst.java"/>
</node>
<node CREATED="1413180307603" ID="ID_1459831976" MODIFIED="1414163218709" TEXT="Implementations">
<node CREATED="1414162438583" ID="ID_1684272514" MODIFIED="1414162459784" TEXT="Project Accacia Package impl.lexer"/>
<node CREATED="1414162490684" ID="ID_683074795" MODIFIED="1414162490684" TEXT="ImplLexer.java">
<node CREATED="1414162495194" ID="ID_736834542" MODIFIED="1414162504964" TEXT="implements Lexer"/>
</node>
<node CREATED="1414162587519" ID="ID_336159897" MODIFIED="1414162617016" TEXT="ImplState.java">
<node CREATED="1414162596625" ID="ID_1784024555" MODIFIED="1414162614509" TEXT="implements StateDesc"/>
</node>
<node CREATED="1414162541961" ID="ID_1540117935" MODIFIED="1414162541961" TEXT="ImplLexerFactory.java">
<node CREATED="1414162544650" ID="ID_1767372632" MODIFIED="1414162556467" TEXT="calls implemented Lexer"/>
</node>
</node>
<node CREATED="1414163121777" ID="ID_205406773" MODIFIED="1414163199264" TEXT="Definitions">
<node CREATED="1414163133582" ID="ID_267324661" MODIFIED="1414163155033" TEXT="Project AcaciaLex"/>
<node CREATED="1414163234987" ID="ID_1666992493" MODIFIED="1414163502021" TEXT="Packages">
<node CREATED="1414163257139" ID="ID_529416874" MODIFIED="1414163260796" TEXT="ebnf"/>
<node CREATED="1414163262038" ID="ID_311017962" MODIFIED="1414163265428" TEXT="impl"/>
<node CREATED="1414163266156" ID="ID_1709859454" MODIFIED="1414163268712" TEXT="sql"/>
<node CREATED="1414163269266" ID="ID_500390113" MODIFIED="1414163271901" TEXT="url"/>
</node>
</node>
<node CREATED="1413180667801" ID="ID_318538522" MODIFIED="1414163203139" TEXT="Interface usages">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Test clases
    </p>
  </body>
</html>
</richcontent>
<node CREATED="1414162870839" ID="ID_592992612" MODIFIED="1414163494285" TEXT="Project AcaciaTest Package commonTest"/>
<node CREATED="1414162903039" ID="ID_924847000" MODIFIED="1414162989635" TEXT="TestCommon.java">
<richcontent TYPE="NOTE"><html>
  <head>
    
  </head>
  <body>
    <p>
      Classes to invoke Lexer tests
    </p>
  </body>
</html>
</richcontent>
<node CREATED="1414163007738" ID="ID_1642357475" MODIFIED="1414163038881" TEXT="initRun()"/>
<node CREATED="1414163015329" ID="ID_1062541752" MODIFIED="1414163042347" TEXT="exprRun()"/>
<node CREATED="1414163024851" ID="ID_1351453178" MODIFIED="1414163045102" TEXT="ebnfRun()"/>
<node CREATED="1414163052131" ID="ID_42912287" MODIFIED="1414163055832" TEXT="urlRun()"/>
</node>
</node>
</node>
<node CREATED="1413180383682" ID="ID_1441042687" MODIFIED="1413180386982" POSITION="left" TEXT="Processor">
<node CREATED="1413180575135" ID="ID_824229058" MODIFIED="1413180594594" TEXT="Walk through">
<node CREATED="1414163427904" ID="ID_795397804" MODIFIED="1414163462012" TEXT="Project Acacia Package proc.lexer"/>
<node CREATED="1414163512985" ID="ID_733756079" MODIFIED="1414163534654" TEXT="ProcessAnnLexer.java">
<node CREATED="1414163585123" ID="ID_26679624" MODIFIED="1414163624572" TEXT="Finds annotated State and Lexer clases"/>
<node CREATED="1414163632486" ID="ID_213920796" MODIFIED="1414163987121" TEXT="Makes copies of implemented interfaces ImplLexer.java and ImplState.java"/>
<node CREATED="1414163675013" ID="ID_318076472" MODIFIED="1414163871705" TEXT="Replace everything between markers //@... and //\@...">
<node CREATED="1414163873788" ID="ID_1041378470" MODIFIED="1414163904844" TEXT="//@addToken"/>
<node CREATED="1414163906477" ID="ID_149786829" MODIFIED="1414163919218" TEXT="text to replace from Lexer definition"/>
<node CREATED="1414163922650" ID="ID_1033567161" MODIFIED="1414163930975" TEXT="//\@addToken"/>
</node>
<node CREATED="1414164067446" ID="ID_1668846035" MODIFIED="1414164089230" TEXT="Creates Lexer implementation classes">
<node CREATED="1414164095310" ID="ID_555329203" MODIFIED="1414164115217" TEXT="Project AcaciaLex">
<node CREATED="1414164116715" ID="ID_457940775" MODIFIED="1414164132366" TEXT="Folder build/classes"/>
</node>
<node CREATED="1414164134719" ID="ID_797078689" MODIFIED="1414164156070" TEXT="UrlLexFactory.java"/>
<node CREATED="1414164157417" ID="ID_812786771" MODIFIED="1414164174321" TEXT="UrlLexImpl.java"/>
<node CREATED="1414164179649" ID="ID_925424172" MODIFIED="1414164186085" TEXT="UrlStateImpl.java"/>
</node>
</node>
</node>
<node CREATED="1413180535446" ID="ID_32097347" MODIFIED="1413180547016" TEXT="Output">
<node CREATED="1414163416154" ID="ID_766890464" MODIFIED="1414163416158" TEXT="        message = &quot;Ended annotation processing ... &quot;;&#xa;        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);&#xa;"/>
</node>
</node>
</node>
</map>
