/*
 * Copyright 2014 Acacia Man
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

package parse.sqlTest;

import common.Common;
import java.io.File;
import java.util.TreeSet;
import lexer.Token;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import parse.sql.Parser;
import parse.sql.SqlManager;
import parse.sql.SqlStatement;
import sql.lexer.SqlLexFactory;
import sql.lexer.SqlLexImpl;
import sql.lexer.SqlState;

public class AsSelectTest {

    final public Logger log = Logger.getLogger(Common.class.getName());

    public AsSelectTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
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
    public void run() throws Exception {
    
        org.apache.log4j.BasicConfigurator.configure();
        TreeSet<String> sTokens = new TreeSet<String>();
        
        SqlLexFactory factory = new SqlLexFactory();
        SqlLexImpl lexer = factory.getSqlLexImpl();

        java.net.URL url = this.getClass().getResource("all_tables.sql");
        File f = new File(url.getFile());
        lexer.setInput(f);
        int countCommented = 0;
//        lexer.run();
        
                Token token;
        while ((token = lexer.findNext()).isFound()) {
            if((token.getCats()&SqlState.COMMENTED)==SqlState.COMMENTED) countCommented++;
            if("Ident".equals(token.getType())) sTokens.add(token.getString(lexer).toUpperCase());
        }
        
        System.out.println("Count commented: " + countCommented);

        for(String s:sTokens) {
            System.out.println(s);
        }
        
        assertTrue(true);
    
    
    }
    
    
    @Test
    public void parse() throws Exception {
    
        org.apache.log4j.BasicConfigurator.configure();
        
        SqlLexFactory factory = new SqlLexFactory();
        SqlLexImpl lexer = factory.getSqlLexImpl();

        java.net.URL url = this.getClass().getResource("all_tables.sql");
        File f = new File(url.getFile());
        lexer.setInput(f);
//        lexer.run();
        
        SqlManager sm = new SqlManager(new Parser(lexer));
        
        sm.parse();
        
        System.out.println("######## Statements:");
        for(SqlStatement s: sm.getSqlStats()) {
            System.out.println(s.getSb().toString());
        }
        
        
        assertTrue(true);
    
    
    }
    
    
}
