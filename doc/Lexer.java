import java.io.Reader;
import java.util.Scanner;
import java.util.HashMap;
import java.util.regex.Pattern;

/** A Lexer produces a stream of tokens taken from an arbitrary Reader
 *  supplied to the constructor, in response to repeated calls to the 
 *  Lexer's nextToken routine.  */
public class Lexer { 

    /** The possible syntactic categories of the tokens returned by
     *  nextToken.  The arguments to the enumerals are the lexemes
     *  corresponding to the Category, when these are unique.  The
     *  Categories EOF and ERROR are artificial; they mark the end
     *  of the token stream and erroneous tokens, respectively. */
    public static enum Category {
        GTEQ(">="), LTEQ("<="), GT(">"), LT("<"), ARROW("-->"),
        PLUS("+"), MINUS("-"), STAR("*"), SLASH("/"), ASSIGN("="),
        LPAR ("("), RPAR (")"), SEMI(";"), COMMA(","),
        IF("if"), DEF("def"), ELSE("else"), FI("fi"), WHILE("while"),
        IDENT(null), NUMERAL(null), EOF(null), ERROR (null);

        final private String lexeme;
        Category (String s) {
            lexeme = s;
        }
    }

    /** The lexeme read by the last call to nextToken.  Undefined after
     *  nextToken returns EOF or before nextToken is called.  Contains
     *  the erroneous character after nextToken returns ERROR. */
    public String lastLexeme;

    /** Mapping of lexemes represented by Categories with single
     *  members to those categories. */
    private static HashMap<String, Category> tokenMap =
        new HashMap<String, Category> ();
    static {
        for (Category c : Category.values ())
            tokenMap.put (c.lexeme, c);
    }

    /** Input source. */
    private Scanner inp;

    /** A pattern that always matches the next token or erroneous
     *  character, except at end of file.  Group 1, if present is
     *  whitespace, group 2 is an identifier, group 3 is a numeral. */
    private static final Pattern tokenPat = 
        Pattern.compile ("(\\s+|#.*)" +
                         "|>=|<=|-->|if|def|else|fi|while" +
                         "|([a-zA-Z][a-zA-Z0-9]*)|(\\d+)" +
                         "|.");

    /** A new Lexer taking input from READER. */		
    public Lexer (Reader reader) {
        inp = new Scanner (reader);
    }

    /** Read the next token, storing it in lastLexeme, and returning
     *  its Category.  Returns EOF at end of file, and ERROR for 
     *  erroneous input (one character). */
    public Category nextToken () {
        if (inp.findWithinHorizon (tokenPat, 0) == null)
            return Category.EOF;
        else {
            lastLexeme = inp.match ().group (0);
            if (inp.match ().start (1) != -1)
                return nextToken ();
            else if (inp.match ().start (2) != -1) 
                return Category.IDENT;
            else if (inp.match ().start (3) != -1)
                return Category.NUMERAL;
            Category result = tokenMap.get (lastLexeme);
            if (result == null)
                return Category.ERROR;
            else
                return result;
        }
    }
}
