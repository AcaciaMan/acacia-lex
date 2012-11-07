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

public class StateInst {
    /**
     * StateInst actual instance
     */
    private StateDesc desc;
    private Object instance;

    public StateInst(StateDesc desc) {
        this.desc = desc;
        this.instance = desc.newStateInstance();
    }

    /**
     * StateInst description - Tokens
     * @return the desc
     */
    public StateDesc getDesc() {
        return desc;
    }

    /**
     * StateInst description - Tokens
     * @param desc the desc to set
     */
    public void setDesc(StateDesc desc) {
        this.desc = desc;
    }

    /**
     * StateInst actual instance
     * @return the instance
     */
    public Object getInstance() {
        return instance;
    }

    /**
     * StateInst actual instance
     * @param instance the instance to set
     */
    public void setInstance(Object instance) {
        this.instance = instance;
    }

}
