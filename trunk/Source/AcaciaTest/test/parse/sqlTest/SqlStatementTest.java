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
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import parse.sql.DBObject;
import parse.sql.DBObjectType;
import parse.sql.Parser;
import parse.sql.SqlManager;
import parse.sql.SqlStatement;
import sql.lexer.SqlLexFactory;
import sql.lexer.SqlLexImpl;

public class SqlStatementTest {
    
    SqlManager manager;
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

        java.net.URL url = this.getClass().getResource("all_objects.sql");
        File f = new File(url.getFile());
        lexer.setInput(f);
//        lexer.run();
        
        manager = new SqlManager();
        Parser parser = new Parser(lexer);
        parser.parse(manager);
        
        
        // load db objects
        java.net.URL urlDb = this.getClass().getResource("objects.csv");
        File fDb = new File(urlDb.getFile());

        manager.loadObjects(fDb);
        

        statement = manager.getSqlStats().get(0);
        
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
      Pattern pattern = Pattern.compile("create .*? view (.*? )as select ", Pattern.CASE_INSENSITIVE);
    // In case you would like to ignore case sensitivity you could use this
    // statement
    // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(statement.sb);
    // Check all occurance
    while (matcher.find()) {
      System.out.print("Start index: " + matcher.start());
      System.out.println(" End index: " + matcher.end() + " ");
      System.out.print("Start index1: " + matcher.start(1));
      System.out.println(" End index1: " + matcher.end(1) + " ");
      System.out.println("Start pars: " + statement.parsIdx.get(matcher.start(1)));
      System.out.println("Start text: " + statement.sPars.get(statement.parsIdx.get(matcher.start(1))).toString());
      System.out.println("End pars: " + statement.parsIdx.get(matcher.end(1)));
      System.out.println("End text: " + statement.sPars.get(statement.parsIdx.get(matcher.end(1))).toString());
      System.out.println(matcher.group(1));
    }

        
        
        assertTrue(true);
    }

    @Test
    public void findDBObjects() {
        
        Integer startIdx = 0;
        Integer endIdx = 0;
        Integer endStatementIdx = 0;
        
        
        //statement.findAsSelect();
      Pattern pattern = Pattern.compile("create .*? view (.*? )as select ", Pattern.CASE_INSENSITIVE);
    // In case you would like to ignore case sensitivity you could use this
    // statement
    // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(statement.sb);
    // Check all occurance
    if (matcher.find()) {
      startIdx = statement.parsIdx.get(matcher.start(1));
      endIdx = statement.parsIdx.get(matcher.end(1));
      endStatementIdx = statement.parsIdx.get(matcher.end());
    } else {
        fail("Not found CREATE statement");
    }

        DBObject obj = null;
        for (Integer i = startIdx; i < endIdx; i++) {
            if ((obj
                    = manager.getDBObject(statement.sPars.get(i).getCharSequence().toString().toUpperCase(),
                            DBObjectType.VIEW)) != null) {
                break;
            }
        }
        if(obj!=null){
            System.out.println("View: " + obj.name);
        } else {
            fail("VIEW not found");
        }

        // get related db objects
        DBObject objRelated = null;
        ArrayList<DBObject> objects = new ArrayList<DBObject>();
        for (Integer i = endStatementIdx; i < statement.sPars.size(); i++) {
            if ((objRelated
                    = manager.getDBObject(statement.sPars.get(i).getCharSequence().toString().toUpperCase(),
                            DBObjectType.ANY)) != null) {
                objects.add(objRelated);
            
            }
        }
        
        if(objects.isEmpty()) fail("No related objects");
        
        for(DBObject o:objects) {
           System.out.println("objRelated: " + o.name + " " + o.type);
        }
        
        assertTrue(true);
    }
    
    
    @Test
    public void getPars() {
        
        TreeMap<Integer,Integer> treeMap = new TreeMap<Integer, Integer>(statement.parsIdx);
        
        for(Integer i:treeMap.keySet()){
            System.out.println("i: " + i 
                    + " pars: " + treeMap.get(i)
                    + " text: " + statement.sPars.get(treeMap.get(i))
            );
        }
        
        assertTrue(true);
    }
    
    @Test
    public void loadDBObjects() {
        
        java.net.URL url = this.getClass().getResource("objects.csv");
        File f = new File(url.getFile());

        manager.loadObjects(f);
        
        for(CharSequence cs: manager.getDbObjects().keySet()) {
            System.out.print("DB Objects: " + cs);
            System.out.println(" type: " + manager.getDbObjects().get(cs).type);
    }
        
        assertTrue(true);
    }
    
    
    
}
