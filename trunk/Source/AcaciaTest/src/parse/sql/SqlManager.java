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

package parse.sql;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import sql.lexer.SqlLexFactory;
import sql.lexer.SqlLexImpl;
import sql.lexer.SqlState;

public class SqlManager {
    
    private HashMap<CharSequence, DBObject> dbObjects = new HashMap<CharSequence, DBObject>();
    
    private ArrayList<SqlStatement> sqlStats = new ArrayList<SqlStatement>();
    
    private SqlManagerState state = SqlManagerState.FIRST;
    private SqlManagerAction action = SqlManagerAction.SKIP;
    private SqlStatement statement;
    
    private final SqlLexFactory factory = new SqlLexFactory();
    private final SqlLexImpl lexer = factory.getSqlLexImpl();
    
    private final SimpleAttributeSet messageGreen = new SimpleAttributeSet();
    private final SimpleAttributeSet messageRed = new SimpleAttributeSet();
    private final SimpleAttributeSet messageYellow = new SimpleAttributeSet();
    
    private Integer createStatementCount;
    private Integer otherStatementCount;

    
    public SqlManager() {
         StyleConstants.setForeground(messageGreen, Color.GREEN);
         StyleConstants.setForeground(messageRed, Color.RED);
         StyleConstants.setForeground(messageYellow, Color.YELLOW);
    }

    public void loadObjects(File f) {
        
        String[] parts;
        DBObjectType type;
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            for(String line = br.readLine(); line != null; line = br.readLine()) {
                parts = line.split(",");
                if(parts.length==2){
                    type = DBObjectType.TABLE;
                    if("VIEW".equalsIgnoreCase(parts[1])) type = DBObjectType.VIEW;
                    dbObjects.put(parts[0], new DBObject(parts[0], type));
                }
            }
            br.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SqlManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SqlManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    public DBObject getDBObject(CharSequence cs,DBObjectType type) {
        DBObject result = dbObjects.get(cs);
        
        if(DBObjectType.ANY.equals(type)) return result;
        
        if(result!=null && result.type != type) return null; 
        
        return result;
    }

    /**
     * @return the dbObjects
     */
    public HashMap<CharSequence, DBObject> getDbObjects() {
        return dbObjects;
    }

    /**
     * @param dbObjects the dbObjects to set
     */
    public void setDbObjects(HashMap<CharSequence, DBObject> dbObjects) {
        this.dbObjects = dbObjects;
    }

    /**
     * @return the sqlStats
     */
    public ArrayList<SqlStatement> getSqlStats() {
        return sqlStats;
    }

    /**
     * @param sqlStats the sqlStats to set
     */
    public void setSqlStats(ArrayList<SqlStatement> sqlStats) {
        this.sqlStats = sqlStats;
    }
    
    public void addPars(Pars p) {
        if (SqlManagerState.FIRST.equals(state)) {
            action = SqlManagerAction.SKIP;
            if ("CREATE".equalsIgnoreCase(p.getCharSequence().toString())) {
                action = SqlManagerAction.APPEND;
                statement = new SqlStatement();
            }
        }

        if(SqlManagerAction.APPEND.equals(action)) {
            statement.addPars(p);
        }
        
        if ((p.getCats() & SqlState.SQL_ENDED) == SqlState.SQL_ENDED) {
            if (SqlManagerAction.APPEND.equals(action)) {
                sqlStats.add(statement);
                createStatementCount++;
            } else {
                otherStatementCount++;
            }
            state = SqlManagerState.LAST;
        }

        if (SqlManagerState.FIRST.equals(state)) {
            state = SqlManagerState.MIDDLE;
        }
        if (SqlManagerState.LAST.equals(state)) {
            state = SqlManagerState.FIRST;
        }
    }
    
    public void parse(File file, StyledDocument doc) throws IOException, BadLocationException {

        createStatementCount = 0;
        otherStatementCount = 0;
        
        lexer.setInput(file);
        Parser parser = new Parser(lexer);
        parser.parse(this);

        
        if(createStatementCount==0&&otherStatementCount==0) {
           doc.insertString(doc.getLength(), "No SQL statements \n", messageRed);
        } else if (createStatementCount==0) {
           doc.insertString(doc.getLength(), "No CREATE statements \n", messageYellow);
        } else {
           doc.insertString(doc.getLength(), "Parsed CREATE statements " + createStatementCount  + "\n", messageGreen);
        }
        
    }

    public void init(File file, StyledDocument doc) throws BadLocationException {
        loadObjects(file);
        if (this.dbObjects.size()>0) {
            doc.insertString(doc.getLength(), "Loaded objects count: "+ this.dbObjects.size() + "\n", messageGreen );
        } else {
            doc.insertString(doc.getLength(), "No DB objects loaded!" + "\n", messageRed );
        }
    }
    
}
