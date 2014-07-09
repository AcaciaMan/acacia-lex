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

public class Parsable {
    
    private StringBuilder sb = new StringBuilder();
    
    private Parser parser;
    
    private int start;

    public Parsable(Parser parser, int start, CharSequence ch) {
        this.parser = parser;
        this.start = start;
        if(ch != null) this.sb.append(ch);
    }

    /**
     * @return the sb
     */
    public StringBuilder getSb() {
        return sb;
    }

    /**
     * @param sb the sb to set
     */
    public void setSb(StringBuilder sb) {
        this.sb = sb;
    }

    /**
     * @return the parser
     */
    public Parser getParser() {
        return parser;
    }

    /**
     * @param parser the parser to set
     */
    public void setParser(Parser parser) {
        this.parser = parser;
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
    
    
    
}
