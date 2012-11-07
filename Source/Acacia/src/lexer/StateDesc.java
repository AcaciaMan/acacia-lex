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

import java.util.List;
import java.util.regex.Matcher;

/**
 * StateDesc description (tokens, relationships, etc.)
 */
public interface StateDesc {

    public void addTokens();

    public List<Token> getTokens();

    public Token findMatchingToken(Token token, Matcher matcher);

    /**
     * @return the lexer
     */
    public Lexer getLexer();

    /**
     * @param lexer the lexer to set
     */
    public void setLexer(Lexer lexer);

    public Object newStateInstance();

    public Object executeMethod(Object instance, int methodNum);

}
