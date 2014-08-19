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

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

public class SqlStatementTest {
    
    SqlStatement statement;
    
    public SqlStatementTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException {
        
        org.apache.log4j.BasicConfigurator.configure();
        
        SqlLexFactory factory = new SqlLexFactory();
        SqlLexImpl lexer = factory.getSqlLexImpl();

        java.net.URL url = this.getClass().getResource("all_tables.sql");
        File f = new File(url.getFile());
        lexer.setInput(f);
//        lexer.run();
        
        SqlManager sm = new SqlManager(new Parser(lexer));
        
        sm.parse();

        statement = sm.getSqlStats().get(0);
        
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
    public void counts() {
        
        System.out.println("Pars count: " + statement.sPars.size());
        assertTrue(true);
    }

    
    @Test
    public void getRelations() {
        
        //statement.findAsSelect();
      Pattern pattern = Pattern.compile("create .*? view (.*?) as select ", Pattern.CASE_INSENSITIVE);
    // In case you would like to ignore case sensitivity you could use this
    // statement
    // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(statement.sb);
    // Check all occurance
    while (matcher.find()) {
      System.out.print("Start index: " + matcher.start());
      System.out.print(" End index: " + matcher.end() + " ");
      System.out.println(matcher.group(1));
    }

        
        
        assertTrue(true);
    }
    
    @Test
    public void getPars() {
        
        for(Integer i:statement.getParsIdx().keySet()){
            System.out.println("i: " + i 
                    + " pars: " + statement.parsIdx.get(i)
                    + " text: " + statement.sPars.get(statement.parsIdx.get(i))
            );
        };
        
        
        assertTrue(true);
    }
    
    
}
