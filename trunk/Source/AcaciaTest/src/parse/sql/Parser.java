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
import java.util.HashMap;
import lexer.Lexer;
import lexer.Token;

public class Parser {
   
    private ArrayList<Pars> sPars = new ArrayList<Pars>();
    private HashMap<Integer, Integer> parsIdx = new HashMap<Integer, Integer>();
    private Lexer lexer;
    private Parsable parsable = new Parsable(this, 0, null);
    private ArrayList<SqlStatement> sqlStats = new ArrayList<SqlStatement>();

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }
    
    public void parse() {
        Token token;
        int prevStatementEnd = 0;
        CharSequence ch;
        while ((token = lexer.findNext()).isFound()) {
            if ("Ident".equals(token.getType())) {
                addPars(new Pars(token, lexer));
            }
            else if ("Spec".equals(token.getType())
                    && ";".equals(token.getString(lexer))) {
                addPars(new Pars(token, lexer));
                ch = this.parsable.getSb();
                sqlStats.add(new SqlStatement(this, prevStatementEnd, ch.subSequence(prevStatementEnd, ch.length())));
                prevStatementEnd = ch.length();
            }

        }
        
        for(Pars p:sPars) {
            System.out.println(p.toString());
        }
        
        System.out.println("######## Statements:");
        for(SqlStatement s:sqlStats) {
            System.out.println(s.getSb().toString());
        }
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

    /**
     * @return the sPars
     */
    public ArrayList<Pars> getsPars() {
        return sPars;
    }

    /**
     * @param sPars the sPars to set
     */
    public void setsPars(ArrayList<Pars> sPars) {
        this.sPars = sPars;
    }

    /**
     * @return the parsIdx
     */
    public HashMap<Integer, Integer> getParsIdx() {
        return parsIdx;
    }

    /**
     * @param parsIdx the parsIdx to set
     */
    public void setParsIdx(HashMap<Integer, Integer> parsIdx) {
        this.parsIdx = parsIdx;
    }

    /**
     * @return the parsable
     */
    public Parsable getParsable() {
        return parsable;
    }

    /**
     * @param parsable the parsable to set
     */
    public void setParsable(Parsable parsable) {
        this.parsable = parsable;
    }
    
    public void addPars(Pars pars) {
        sPars.add(pars);
        parsIdx.put(parsable.getSb().length(), sPars.size()-1);
        parsable.getSb().append(pars.getCharSequence()+" ");
    }
    
}
