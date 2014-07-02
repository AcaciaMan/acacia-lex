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

package lexer;

public interface Lexer {

    public Token findNext();
    
    public StateInst pushState(StateInst state);
    public StateInst popState();

    public void setInput(CharSequence input);
    public CharSequence getInput();
    
    public java.io.File getFile();

    public Token getToken();
    public java.util.TreeSet<Token> getTokens();

    public Status getStatus();

    public void run();

        /**
     * Index of the character in CharSequence, where started last search for the return Object
     * @return the start
     */
    public int getStart();
    /**
     * Index of the character in CharSequence, where started last search for the return Object
     * @param start the start to set
     */
    public void setStart(int start);
    /**
     * Index of the last character + 1 for the last found token
     * Next token search starts at the previous search end
     * @return the end
     */
    public int getPosition();
    /**
     * Index of the last character + 1 for the last found token
     * Next token search starts at the previous search end
     * @param position the position to set
     */
    public void setPosition(int position);

    public java.util.regex.Matcher getMatcher();

    public void setInput(java.io.Reader reader) throws java.io.IOException;

    public void setInput(java.io.InputStream is) throws java.io.IOException;

    public void setInput(java.io.File f) throws java.io.FileNotFoundException, java.io.IOException;

    public StringBuilder replace(java.util.Map<String,Object> m);
    
    /**
     * @return the line
     */
    public int getLine();

    /**
     * @param line the line to set
     */
    public void setLine(int line);

    /**
     * @return the lineStart
     */
    public int getLineStart();

    /**
     * @param lineStart the lineStart to set
     */
    public void setLineStart(int lineStart);
    
    public int getColumn();
    
}
