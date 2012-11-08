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
package impl.lexer.lex;

import impl.ImplUtility;
import impl.lexer.common.SimpleToken;
import java.util.regex.Matcher;
import lexer.Token;

@ann.lexer.AnnState
public class ExprState {

    @ann.lexer.AnnTokens({
        @ann.lexer.AnnToken(type = "NUMBER", value = "-?[0-9]+"),
        @ann.lexer.AnnToken(type = "BINARYOP", value = "[*|/|+|-]"),
        @ann.lexer.AnnToken(type = "WHITESPACE", value = "[ \t\f\r\n]+")
    })
    public String getStr(lexer.Lexer lexer) {
        return lexer.getToken().getString();
    }
}
