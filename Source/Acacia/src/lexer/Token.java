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
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Token implements Comparable<Token>{

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
     * token method order number in status class
     */
    private int methodNum;
    
    /**
     * token order number in status class
     */
    private int orderNum;
    
    private TokenStatus status = TokenStatus.NOT_MATCHED;

    public Token() {
    }

    public Token(StateDesc desc, String type, String regularExpression, int methodNum) {
        this.desc = desc;
        this.type = type;
        this.regularExpression = regularExpression;
        this.pattern = Pattern.compile(regularExpression);
        this.methodNum = methodNum;
        this.status = TokenStatus.NOT_MATCHED;
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
        return TokenStatus.FOUND.equals(status);
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

    public void findToken(Matcher matcher, int startFind) {
        matcher.usePattern(getPattern());
        if (matcher.find(startFind)) {
                setStart(matcher.start());
                setEnd(matcher.end());
                setStatus(TokenStatus.FOUND);
            }
        else {
            setStatus(TokenStatus.NOT_FOUND);
        }
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
     * token method order number in status class
     * @return the methodNum
     */
    public int getMethodNum() {
        return methodNum;
    }

    /**
     * token method order number in status class
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

    /**
     * @return the orderNum
     */
    public int getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum the orderNum to set
     */
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * @return the status
     */
    public TokenStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(TokenStatus status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(this.status).
            append(this.start).
            append(this.getLength()).
            append(this.orderNum).    
            toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Token))
            return false;
        if (obj == this)
            return true;

        Token rhs = (Token) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(this.status, rhs.status).
            append(this.start, rhs.start).
            append(this.getLength(), rhs.getLength()).
            append(this.orderNum, rhs.orderNum).    
            isEquals();
    
    }

    @Override
    public int compareTo(Token t) {

        int d = status.compareTo(t.status);
        if (d == 0) {
            d = start - t.start;
        }
        if (d == 0) {
            d = t.getLength() - getLength();
        }
        if (d == 0) {
            d = orderNum - t.orderNum;
        }
        
        return d;
    
    }

}
