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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Token {

    private StateDesc desc;

    private String type;

    private String regularExpression;
    private Pattern pattern;

    /**
     * If input CharSequence matches the token, then start is match.start() and end is match.end()
     */
    private int start = 0;
    private int end = 0;
    /**
     * Result object
     */
    private Object object;

    /**
     * if Lexer.findNext founds this token, then this property is set to true
     */
    private boolean found;
    /**
     * token method order number in state class
     */
    private int methodNum;

    public Token() {
    }

    public Token(StateDesc desc, String type, String regularExpression, int methodNum) {
        this.desc = desc;
        this.type = type;
        this.regularExpression = regularExpression;
        this.pattern = Pattern.compile(regularExpression);
        this.methodNum = methodNum;
        this.found = false;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStart() {
        return start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getEnd() {
        return end;
    }

    public int getLength() {
        return end - start;
    }

    /**
     * @return the regularExpression
     */
    public String getRegularExpression() {
        return regularExpression;
    }

    /**
     * @param regularExpression the regularExpression to set
     */
    public void setRegularExpression(String regularExpression) {
        this.regularExpression = regularExpression;
    }

    /**
     * Result object
     * @return the object
     */
    public Object getObject() {
        return object;
    }

    /**
     * Result object
     * @param object the object to set
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * if Lexer.findNext founds this token, then this property is set to true
     * @return the found
     */
    public boolean isFound() {
        return found;
    }

    /**
     * if Lexer.findNext founds this token, then this property is set to true
     * @param found the found to set
     */
    public void setFound(boolean found) {
        this.found = found;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the desc
     */
    public StateDesc getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(StateDesc desc) {
        this.desc = desc;
    }

    public Token findMatchingToken(Token token, Matcher matcher) {
        Token result = token;
        matcher.usePattern(getPattern());
        if (matcher.lookingAt()) {
            // if matched regular expression is longer
            if (result.getLength() < matcher.end() - matcher.start()) {
                setStart(matcher.start());
                setEnd(matcher.end());
                result = this;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        String result = this.type;
        String objStr;
        if (object!=null) {
            objStr = object.toString();
        } else {
            objStr = "null";
        }
        
        String resStr = this.getString();
        
        if(objStr.equals(resStr)) {
          result = result + " STR(" + resStr +")";
        } else {
          result = result + " " + objStr + " STR(" + resStr +")";
        }

        return result;
    }

    /**
     * token method order number in state class
     * @return the methodNum
     */
    public int getMethodNum() {
        return methodNum;
    }

    /**
     * token method order number in state class
     * @param methodNum the methodNum to set
     */
    public void setMethodNum(int methodNum) {
        this.methodNum = methodNum;
    }

    /**
     * returns token's found string
     * @return found string
     */
    public String getString() {
        return desc.getLexer().getInput().subSequence(getStart(), getEnd()).toString();
    }

}
