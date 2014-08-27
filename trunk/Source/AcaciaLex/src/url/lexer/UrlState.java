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

package url.lexer;

@ann.lexer.AnnState
public class UrlState {
    
    boolean commented = false;
    
    @ann.lexer.AnnToken(type = "Comment start", value = "<![ \\r\\n\\t]*-")
    public String setCommentStart(lexer.Lexer lexer) {
        commented = true;
        return " ";
    }

    @ann.lexer.AnnToken(type = "Comment end", value = "-[ \\r\\n\\t]*>")
    public String setCommentEnd(lexer.Lexer lexer) {
        commented = false;
        return " ";
    }
    
    
    @ann.lexer.AnnToken(type = "URL", value = "http:[A-z0-9./~%]+")
    public String getStr(lexer.Lexer lexer) {
        return " ";
    }
    
}
