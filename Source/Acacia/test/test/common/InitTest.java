/*
 * Copyright 2012 Acacia Man
 * The program is distributed under the terms of the GNU General Public License
 * 
 * This file is part of acacia-lex.
 *
 * acacia-lex is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * acacia-lex is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with acacia-lex.  If not, see <http://www.gnu.org/licenses/>.
 */ 

package test.common;

import lexer.Lexer;
import impl.lexer.ImplLexerFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class InitTest {

    public InitTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void initRun() {
        ImplLexerFactory factory = new ImplLexerFactory();
        Lexer lexer = factory.getImplLexer();
        CharSequence charSequence = "SELECTS * \n. from .. else";
        
        lexer.setInput(charSequence);
        lexer.run();
        assertTrue(true);
    }

        @Test
    public void checkPattern() {
        String charSequence = "SELECTS * <!- from .. else";

           Pattern pattern = Pattern.compile("<![ \\r\\n\\t]*-");
    Matcher matcher = pattern.matcher(charSequence);
        
            System.out.println("Matches " + matcher.find());
        assertTrue(true);
    }

    
}