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

package impl.lexer;

import impl.lexer.common.SimpleState;
import impl.lexer.common.SimpleToken;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import lexer.Lexer;
import lexer.StateDesc;
import lexer.Token;

public class ImplState implements StateDesc {

    protected List<Token> tokens = new ArrayList<Token>();
    protected Lexer lexer;

    public ImplState(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public void addTokens() {

        Integer L = 3;
        Integer N = 0;
        char C = 3;
        //@values
        //@valueArr
        char[] charArrayZ = new char[L];
        //\@valueArr
        //@valueChar
        charArrayZ[N] = C;
        //\@valueChar

        charArrayZ[1] = 3;

        char[] charArray2 = new char[3];
        charArray2[0] = 3;

        //\@values


        Token newToken;

        //@newTokens
        //@addToken
        newToken = new Token(
                this,
                "SimpleToken.SELECT",
                "SELECTS",
                0);
        tokens.add(newToken);
        newToken.setOrderNum(tokens.size());
        //\@addToken

        newToken = new Token(
                this,
                SimpleToken.FROM,
                "FROM",
                0);
        tokens.add(newToken);
        newToken.setOrderNum(tokens.size());

        newToken = new Token(
                this,
                SimpleToken.DOT,
                ".",
                1);
        tokens.add(newToken);
        newToken.setOrderNum(tokens.size());
        //\@newTokens

    }

    @Override
    public List<Token> getTokens() {
        return this.tokens;
    }

    @Override
    public Token findMatchingToken(Token token, Matcher matcher) {
        Token result = token;
        for (Token t : this.tokens) {
            result = t.findMatchingToken(result, matcher);
        }
        return result;
    }

    /**
     * @return the lexer
     */
    @Override
    public Lexer getLexer() {
        return lexer;
    }

    /**
     * @param lexer the lexer to set
     */
    @Override
    public void setLexer(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public Object newStateInstance() {
        //@instanceClass
        return new SimpleState();
        //\@instanceClass

    }

    @Override
    public Object executeMethod(Object instance, int methodNum) {
        Object result = null;
        switch(methodNum) {
          //@tokenMethods
          //@tokenMethod
          case(0):
              result = ((SimpleState) instance).returnPlus(lexer);
              break;
          //\@tokenMethod
          case(1):
              result = ((SimpleState) instance).returnDot(lexer);
              break;
          //\@tokenMethods
          default:
              break;

        }
        return result;
    }

}
