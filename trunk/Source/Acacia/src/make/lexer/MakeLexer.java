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

import ann.lexer.AnnLexer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class MakeLexer extends MakeManager {

    final String B_SOUT = "bSout";

    public MakeLexer(TypeElement clazz, ProcessingEnvironment processingEnv, EnumMakeClass enumMakeClass) {
        super(clazz, processingEnv, enumMakeClass);
    }

    @Override
    public void replaceImplParameters() {
        super.replaceImplParameters();
        
        AnnLexer annLexer = this.getClazz().getAnnotation(AnnLexer.class);

        if (!annLexer.bSout()) {
        String message = "Removing System.out.println ... ";
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
        String replace = getReplacements().getFragment(B_SOUT);
        replace = " ";
        getReplacements().replaceAllIdentified(B_SOUT, replace);
        }
    
    }
    

}
