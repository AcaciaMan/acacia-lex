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

import lexer.Lexer;
import lexer.Token;

public class Pars{
    
    private String type;
    private int start = 0;
    private int end = 0;

    private Object object;
    
    private int line;
    private int column;
    
    private Lexer lexer;

    public Pars() {
    }

    public Pars(Token t, Lexer l) {
        this.type = t.getType();
        this.start = t.getStart();
        this.end = t.getEnd();
        this.object = t.getObject();
        this.line = l.getLine();
        this.column = l.getColumn();
        this.lexer = l;
    }

    public int getLength() {
        return end - start;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public int getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * @return the object
     */
    public Object getObject() {
        return object;
    }

    /**
     * @param object the object to set
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * @return the line
     */
    public int getLine() {
        return line;
    }

    /**
     * @param line the line to set
     */
    public void setLine(int line) {
        this.line = line;
    }

    /**
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * @return the lexer
     */
    public Lexer getLexer() {
        return lexer;
    }

    /**
     * @param lexer the lexer to set
     */
    public void setLexer(Lexer lexer) {
        this.lexer = lexer;
    }
    
    public CharSequence getCharSequence() {
        return lexer.getInput().subSequence(getStart(), getEnd());
    }

    @Override
    public String toString() {
        return getCharSequence()+" Ln:"+getLine()+" Col:"+getColumn(); 
    }
    
}
