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

package common;

import ebnf.lexer.EbnfLexFactory;
import ebnf.lexer.EbnfLexImpl;
import impl.lexer.lex.ExprLexFactory;
import impl.lexer.lex.ExprLexImpl;
import impl.lexer.lex.LexClassFactory;
import impl.lexer.lex.LexClassImpl;
import java.io.File;
import java.io.IOException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Common {

    final public Logger log = Logger.getLogger(Common.class.getName());

    public Common() {
    }

    public void initRun() {
        LexClassFactory factory = new LexClassFactory();
        LexClassImpl lexer = factory.getLexClassImpl();
        //CharSequence charSequence = "SELECT FROM ... ELSE";

        File f = new File("..\\Acacia/src/impl/lexer/ImplLexerFactory.java");
        try {
            lexer.setInput(f);
        } catch (IOException ex) {
            log.log(Level.FATAL, "IOException", ex);
        }
        lexer.run();
    }

    public void exprRun() {
        ExprLexFactory factory = new ExprLexFactory();
        ExprLexImpl lexer = factory.getExprLexImpl();
        CharSequence charSequence = "11 + 22 + 33";
        lexer.setInput(charSequence);
        lexer.run();
    }

    public void ebnfRun() {
        EbnfLexFactory factory = new EbnfLexFactory();
        EbnfLexImpl lexer = factory.getEbnfLexImpl();
        CharSequence charSequence =
"(* a simple program syntax in EBNF − Wikipedia *) " +
"program = 'PROGRAM', white space, identifier, white space, "+
"           'BEGIN', white space, "+
"           { assignment, \";\", white space }, " +
"           'END.' ;";
        lexer.setInput(charSequence);
        lexer.run();
    }



}
