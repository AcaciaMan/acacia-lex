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

package impl;

import java.io.IOException;
import java.io.Reader;

public class ImplUtility {

    final int DEFAULT_BUFFER_SIZE = 1024;

    public ImplUtility() {
    }

    public CharSequence capitalize(CharSequence name) {
        StringBuilder b = new StringBuilder(name);
        char c = b.charAt(0);
        b.setCharAt(0, Character.toUpperCase(c));
        return b;
    }

    public StringBuilder getCharSequenceFromReader(Reader reader) throws IOException {
        StringBuilder result = new StringBuilder();
        char[] buf = new char[DEFAULT_BUFFER_SIZE];
        int n;
        while ((n = reader.read(buf)) >= 0) {
            result.append(buf, 0, n);
        }
        return result;
    }

}
