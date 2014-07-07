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

import java.util.TreeSet;
import lexer.Lexer;
import lexer.Token;

public class Parser {
   
    private TreeSet<Token> sToken = new TreeSet<Token>();
    Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }
    
    public void parse() {
                Token token;
        while ((token = lexer.findNext()).isFound()) {
            if("Ident".equals(token.getType())) sToken.add(new Token(token));
        }
    }

    /**
     * @return the sToken
     */
    public TreeSet<Token> getsToken() {
        return sToken;
    }

    /**
     * @param sToken the sToken to set
     */
    public void setsToken(TreeSet<Token> sToken) {
        this.sToken = sToken;
    }
    
}
