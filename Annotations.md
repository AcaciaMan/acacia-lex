![https://acacia-lex.googlecode.com/svn/wiki/FreeMind/Annotations.png](https://acacia-lex.googlecode.com/svn/wiki/FreeMind/Annotations.png)

https://acacia-lex.googlecode.com/svn/wiki/FreeMind/Annotations.mm

<p>Annotations<br>
<ul><li>Declarations<br>
<ul><li>Project AcaciaAnn<br>
<br>
<br>
<li>AnnState.java<br>
<br>
AnnToken.java<br>
<br>
AnnTokens.java<br>
<br>
AnnUnknown.java<br>
<br>
AnnLexer.java<br>
<br>
AnnStartState.java<br>
<br>
<br>
<br>
<br>
<br>
<li>Processor<br>
<ul><li>Walk through<br>
<ul><li>Project Acacia Package proc.lexer<br>
<br>
<br>
<li>ProcessAnnLexer.java<br>
<ul><li>Finds annotated State and Lexer clases<br>
<br>
<br>
<li>Makes copies of implemented interfaces ImplLexer.java and ImplState.java<br>
<br>
<br>
<li>Replace everything between markers //@... and //\@...<br>
<ul><li>//@addToken<br>
<br>
<br>
<li>text to replace from Lexer definition<br>
<br>
<br>
<li>//\@addToken<br>
<br>
<br>
<br>
<br>
<br>
<li>Creates Lexer implementation classes<br>
<ul><li>Project AcaciaLex<br>
<ul><li>Folder build/classes<br>
<br>
<br>
<br>
<br>
<br>
<li>UrlLexFactory.java<br>
<br>
<br>
<li>UrlLexImpl.java<br>
<br>
<br>
<li>UrlStateImpl.java<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<li>Output<br>
<ul><li><p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;message = "Ended annotation processing ... ";<br>
<br>
<blockquote>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);<br>
<br></blockquote>







<li>Docs<br>
<ul><li><p>Source -&gt; Browse -&gt; svn -&gt; doc<br>
<p>AnnotationProcessing_Eclipse - acris - How to setup annotation processors with eclipse - Web framework for building applications, web sites and portals using Google Web Toolkit - Google Project Hosting.htm<br>
<p>Code Generation using Annotation Processors in the Java language &#8211;&nbsp;part 2 Annotation Processors dr. macphail's trance.htm<br>
<p>Lexer.java<br>
<p>Writing a Lexer in Java 1.7 using Regex Named Capturing Groups.htm<br>
<br>
<br>
<br>
<li>Lexer<br>
<ul><li>Interfaces<br>
<ul><li>Project Accacia Package lexer<br>
<br>
<br>
<li>Lexer.java<br>
<br>
<br>
<li>Token.java<br>
<br>
<br>
<li>Status.java<br>
<br>
<br>
<li>StateDesc.java<br>
<br>
<br>
<li>StateInst.java<br>
<br>
<br>
<br>
<br>
<br>
<li>Implementations<br>
<ul><li>Project Accacia Package impl.lexer<br>
<br>
<br>
<li>ImplLexer.java<br>
<ul><li>implements Lexer<br>
<br>
<br>
<br>
<br>
<br>
<li>ImplState.java<br>
<ul><li>implements StateDesc<br>
<br>
<br>
<br>
<br>
<br>
<li>ImplLexerFactory.java<br>
<ul><li>calls implemented Lexer<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<li>Definitions<br>
<ul><li>Project AcaciaLex<br>
<br>
<br>
<li>Packages<br>
<ul><li>ebnf<br>
<br>
<br>
<li>impl<br>
<br>
<br>
<li>sql<br>
<br>
<br>
<li>url<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<li>Interface usages<br>
<ul><li>Project AcaciaTest Package commonTest<br>
<br>
<br>
<li>TestCommon.java<br>
<ul><li>initRun()<br>
<br>
<br>
<li>exprRun()<br>
<br>
<br>
<li>ebnfRun()<br>
<br>
<br>
<li>urlRun()<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
