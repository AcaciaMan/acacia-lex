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

package sql.lexer;

@ann.lexer.AnnState
public class SqlState {
    
    private boolean commentMulti = false;
    private boolean commentSingle = false;
    private boolean commented = false;
    
    @ann.lexer.AnnToken(type = "Spec", value = "/\\*|\\*/|--|[\\r\\n]+")
    public String setCommentStart(lexer.Lexer lexer) {
        String result = lexer.getToken().getString();
        if(!isCommented()) {
            if("/\\*".equals(result)) setCommentMulti(true);
            if("--".equals(result)) setCommentSingle(true);
        }

        if(isCommented()) {
            if("\\*/".equals(result)) setCommentMulti(false);
            if(result.contains("\\r")||result.contains("\\n")) setCommentSingle(false);
        }
        
        return result;
    }

    @ann.lexer.AnnToken(type = "Ident", value = "[A-z0-9_$#]+")
    public String getStr(lexer.Lexer lexer) {
        return lexer.getToken().getString();
    }

    /**
     * @return the commentMulti
     */
    public boolean isCommentMulti() {
        return commentMulti;
    }

    /**
     * @param commentMulti the commentMulti to set
     */
    public void setCommentMulti(boolean commentMulti) {
        this.commentMulti = commentMulti;
        this.commented = commentMulti;
    }

    /**
     * @return the commentSingle
     */
    public boolean isCommentSingle() {
        return commentSingle;
    }

    /**
     * @param commentSingle the commentSingle to set
     */
    public void setCommentSingle(boolean commentSingle) {
        this.commentSingle = commentSingle;
        this.commented = commentSingle;
    }

    /**
     * @return the commented
     */
    public boolean isCommented() {
        return commented;
    }

    /**
     * @param commented the commented to set
     */
    public void setCommented(boolean commented) {
        this.commented = commented;
    }
    
    
    
}
