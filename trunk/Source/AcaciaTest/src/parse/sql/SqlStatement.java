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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlStatement extends Parsable {
    private DBObject obj = null;
    private ArrayList<DBObject> objects = new ArrayList<DBObject>();
    
    public void findViewAsSelect(SqlManager manager) {

        Integer startIdx = 0;
        Integer endIdx = 0;
        Integer endStatementIdx = 0;
        
        
        //statement.findAsSelect();
      Pattern pattern = Pattern.compile("create .*? view (.*? )as select ", Pattern.CASE_INSENSITIVE);
    // In case you would like to ignore case sensitivity you could use this
    // statement
    // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(sb);
    // Check all occurance
    if (matcher.find()) {
      startIdx = parsIdx.get(matcher.start(1));
      endIdx = parsIdx.get(matcher.end(1));
      endStatementIdx = parsIdx.get(matcher.end());
    } else {
        return;
    }

        for (Integer i = startIdx; i < endIdx; i++) {
            if ((obj
                    = manager.getDBObject(sPars.get(i).getCharSequence().toString().toUpperCase(),
                            DBObjectType.VIEW)) != null) {
                break;
            }
        }
        if(obj==null){
           return;
        }

        // get related db objects
        DBObject objRelated = null;
        for (Integer i = endStatementIdx; i < sPars.size(); i++) {
            if ((objRelated
                    = manager.getDBObject(sPars.get(i).getCharSequence().toString().toUpperCase(),
                            DBObjectType.ANY)) != null) {
                objects.add(objRelated);
            
            }
        }

    }

    /**
     * @return the obj
     */
    public DBObject getObj() {
        return obj;
    }

    /**
     * @param obj the obj to set
     */
    public void setObj(DBObject obj) {
        this.obj = obj;
    }

    /**
     * @return the objects
     */
    public ArrayList<DBObject> getObjects() {
        return objects;
    }

    /**
     * @param objects the objects to set
     */
    public void setObjects(ArrayList<DBObject> objects) {
        this.objects = objects;
    }
    
}
