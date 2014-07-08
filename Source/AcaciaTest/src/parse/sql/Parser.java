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
   
    private TreeSet<Pars> sPars = new TreeSet<Pars>();
    private Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }
    
    public void parse() {
        Token token;
        while ((token = lexer.findNext()).isFound()) {
            if ("Ident".equals(token.getType())) {
                sPars.add(new Pars(token, lexer));
            }
            if ("Spec".equals(token.getType())
                    && ";".equals(token.getString(lexer))) {
                sPars.add(new Pars(token, lexer));
            }

        }
        
        for(Pars p:sPars) {
            System.out.println(p.toString());
        }
    }

    /**
     * @return the sPars
     */
    public TreeSet<Pars> getsPars() {
        return sPars;
    }

    /**
     * @param sPars the sPars to set
     */
    public void setsPars(TreeSet<Pars> sPars) {
        this.sPars = sPars;
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
    
}
