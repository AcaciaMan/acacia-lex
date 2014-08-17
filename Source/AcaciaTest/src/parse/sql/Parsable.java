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

package parse.sql;

import java.util.ArrayList;
import java.util.HashMap;

public class Parsable {

    public ArrayList<Pars> sPars = new ArrayList<Pars>();
    public HashMap<Integer, Integer> parsIdx = new HashMap<Integer, Integer>();
    
    public StringBuilder sb = new StringBuilder();


    /**
     * @return the sb
     */
    public StringBuilder getSb() {
        return sb;
    }

    /**
     * @param sb the sb to set
     */
    public void setSb(StringBuilder sb) {
        this.sb = sb;
    }

    /**
     * @return the sPars
     */
    public ArrayList<Pars> getsPars() {
        return sPars;
    }

    /**
     * @param sPars the sPars to set
     */
    public void setsPars(ArrayList<Pars> sPars) {
        this.sPars = sPars;
    }

    /**
     * @return the parsIdx
     */
    public HashMap<Integer, Integer> getParsIdx() {
        return parsIdx;
    }

    /**
     * @param parsIdx the parsIdx to set
     */
    public void setParsIdx(HashMap<Integer, Integer> parsIdx) {
        this.parsIdx = parsIdx;
    }
    
    public void addPars(Pars pars) {
        sPars.add(pars);
        parsIdx.put(sb.length(), sPars.size()-1);
        sb.append(pars.getCharSequence()).append(" ");
    }
    
}
