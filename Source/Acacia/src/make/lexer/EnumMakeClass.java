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

package make.lexer;

public enum EnumMakeClass {

    Lexer("ImplLexer", MakeManager.IMPL),
    LexerFactory("ImplLexerFactory", MakeManager.FACTORY),
    State("ImplState", MakeManager.IMPL);

    private String name;
    private String ext;

    private EnumMakeClass(String name, String ext) {
        this.name = name;
        this.ext = ext;
    }

    public String getExt() {
        return ext;
    }

    public String getName() {
        return name;
    }

}
