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

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Pool for lexer objects
 * For example, State descriptions have only one instance in lexer for each State description class
 */
public class Pool {

    /**
     * State description class maping to StateDesc instance
     */
    Map<Class<? extends StateDesc>, StateDesc> stateDescs = new HashMap<Class<? extends StateDesc>, StateDesc>();

    public StateDesc getStateDesc(Class<? extends StateDesc> stateDesc, Lexer lexer){

        StateDesc result;
        if((result=stateDescs.get(stateDesc))!=null) return result;
        try {
            Constructor c = stateDesc.getConstructor(Lexer.class);
            result = (StateDesc) c.newInstance(lexer);
            result.addTokens();
        } catch (Exception ex) {
            Logger.getLogger(Pool.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
        stateDescs.put(stateDesc, result);
        return result;

    }

}
