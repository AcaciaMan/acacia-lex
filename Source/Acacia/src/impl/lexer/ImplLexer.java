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

import impl.ImplUtility;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lexer.Lexer;
import lexer.Pool;
import lexer.StateDesc;
import lexer.StateInst;
import lexer.Status;
import lexer.Token;

public class ImplLexer implements Lexer {

    protected Token curToken;
    protected Pool pool = new Pool();
    /**
     * lexer status; push or not to push new state, etc.
     */
    protected Status status = new Status();
    protected Stack<StateInst> states = new Stack<StateInst>();
    protected CharSequence input;
    /**
     * Index of the character in CharSequence, where started last search for the return Object
     */
    protected int start = 0;
    /**
     * Index of the last character + 1 for the last found token
     * Next token search starts at the previous search end
     */
    protected int position = 0;
    protected Matcher matcher = Pattern.compile("").matcher("");
    /**
     * Empty token for searching process initialization
     */
    protected Token emptyToken = new Token();

    public ImplLexer() {
    }

    @Override
    public StateInst pushState(StateInst state) {
        return states.push(state);
    }

    @Override
    public StateInst popState() {
        return states.pop();
    }

    public Token findNextToken() {

        Token nextToken = emptyToken;
        matcher.region(position, input.length());

        // Current state
        nextToken = states.peek().getDesc().findMatchingToken(nextToken, matcher);
        // found next token
        if (nextToken.getLength() > 0) {
            nextToken.setFound(true);
        }

        return nextToken;
    }

    /**
     * Iterates through all appropriate regular expressions and searches for the longest match
     * @return Object returned by lonngest matched regular expression
     */
    @Override
    public Token findNext() {
        start = this.position;
        status.init();
        while ((curToken = findNextToken()).isFound()) {
            this.position = this.position + curToken.getLength();
            processToken();

            if (this.status.isNoObject() || (curToken.getObject() != null)) {
                break;
            }
            status.init();
        }

        return curToken;
    }

    private void processToken() {
        //Execute token state method
        Object curTokenObject = curToken.getDesc().executeMethod(
                states.peek().getInstance(),
                curToken.getMethodNum());
        curToken.setObject(curTokenObject);

        System.out.println("FOUND " + curToken.toString());
    }

    @Override
    public void setInput(CharSequence input) {
        this.input = input;
        this.position = 0;
        matcher.reset(input);
    }

    @Override
    public CharSequence getInput() {
        return input;
    }

    @Override
    public void run() {
        Token token;
        while ((token = this.findNext()).isFound()) {
            System.out.println("LEXER RES = " + token.toString());

        }
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public Token getToken() {
        return curToken;
    }

    /**
     * Index of the character in CharSequence, where started last search for the return Object
     * @return the start
     */
    @Override
    public int getStart() {
        return start;
    }

    /**
     * Index of the character in CharSequence, where started last search for the return Object
     * @param start the start to set
     */
    @Override
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * Index of the last character + 1 for the last found token
     * Next token search starts at the previous search end
     * @return the end
     */
    @Override
    public int getPosition() {
        return position;
    }

    /**
     * Index of the last character + 1 for the last found token
     * Next token search starts at the previous search end
     * @param position the position to set
     */
    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public Matcher getMatcher() {
        Matcher m = null;

        m = curToken.getPattern().matcher(input);
        m.region(curToken.getStart(), input.length());
        m.lookingAt();

        return m;
    }

    public void addStartState(Class<? extends StateDesc> stateDesc) {
        StateDesc desc = this.pool.getStateDesc(stateDesc, this);
        StateInst inst = new StateInst(desc);
        this.pushState(inst);
    }

    @Override
    public void setInput(Reader reader) throws IOException {
        ImplUtility utility = new ImplUtility();
        StringBuilder result = utility.getCharSequenceFromReader(reader);
        this.setInput(result);
    }

    @Override
    public void setInput(InputStream is) throws IOException {
        Reader reader = new InputStreamReader(is);
        try {
            this.setInput(reader);
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            reader.close();
        }
    }

    @Override
    public void setInput(File f) throws FileNotFoundException, IOException {
        Reader reader = new FileReader(f);
        try {
            this.setInput(reader);
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            reader.close();
        }
    }

    @Override
    public StringBuilder replace(Map<String, Object> m) {
        StringBuilder result = null;
        Token token = null;
        String replacement;

        result = new StringBuilder(input);

        int offset = 0;
        while ((token = this.findNext()).isFound()) {
            if (m.containsKey(token.getType())) {
                replacement = m.get(token.getType()).toString();
                result.replace(token.getStart() + offset, token.getEnd() + offset, replacement);
                offset = offset + replacement.length() - token.getLength();
            }
        }

        return result;
    }
}
