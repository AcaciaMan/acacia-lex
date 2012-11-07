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

package impl.lexer.common;

import ann.lexer.AnnToken;
import ann.lexer.AnnTokens;
import lexer.Lexer;

@ann.lexer.AnnState
public class SimpleState {

    @AnnTokens({
        @AnnToken(type=SimpleToken.SELECT,value="SELECT"),
        @AnnToken(type=SimpleToken.FROM,value="FROM")
    })
    public String returnPlus(Lexer lexer) {
        System.out.println("Test Token return puls sign");
        lexer.getStatus().setNoObject(true);
        return null;
    }

    @AnnToken(type = SimpleToken.SELECT, value = "SELECT")
    public String returnDot(Lexer lexer) {
        System.out.println("Test Token return dot");
        return lexer.getToken().getString();
    }



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

}
