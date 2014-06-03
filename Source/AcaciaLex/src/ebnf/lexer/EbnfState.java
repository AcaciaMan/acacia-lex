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
package ebnf.lexer;

@ann.lexer.AnnState
public class EbnfState {

    @ann.lexer.AnnTokens({
        @ann.lexer.AnnToken(type = EbnfTypes.definition, value = "="),
        @ann.lexer.AnnToken(type = EbnfTypes.concatenation, value = ","),
        @ann.lexer.AnnToken(type = EbnfTypes.termination, value = ";"),
        @ann.lexer.AnnToken(type = EbnfTypes.alternation, value = "\\|"),
        @ann.lexer.AnnToken(type = EbnfTypes.option_start, value = "\\["),
        @ann.lexer.AnnToken(type = EbnfTypes.option_end, value = "\\]"),
        @ann.lexer.AnnToken(type = EbnfTypes.repetition_start, value = "\\{"),
        @ann.lexer.AnnToken(type = EbnfTypes.repetition_end, value = "\\}"),
        @ann.lexer.AnnToken(type = EbnfTypes.grouping_start, value = "\\("),
        @ann.lexer.AnnToken(type = EbnfTypes.grouping_end, value = "\\)"),
        @ann.lexer.AnnToken(type = EbnfTypes.terminal_string, value = "\""),
        @ann.lexer.AnnToken(type = EbnfTypes.terminal_string, value = "'"),
        @ann.lexer.AnnToken(type = EbnfTypes.comment_start, value = "\\(\\*"),
        @ann.lexer.AnnToken(type = EbnfTypes.comment_end, value = "\\*\\)"),
        @ann.lexer.AnnToken(type = EbnfTypes.special_sequence, value = "\\?"),
        @ann.lexer.AnnToken(type = EbnfTypes.exception, value = "-"),
        @ann.lexer.AnnToken(type = EbnfTypes.whitespaces, value = "\\s+"),
        @ann.lexer.AnnToken(type = EbnfTypes.identifier, value = "\\w+")})
    public String getStr(lexer.Lexer lexer) {
        return lexer.getToken().getString();
    }
}
