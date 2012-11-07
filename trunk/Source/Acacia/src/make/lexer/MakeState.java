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

import ann.lexer.AnnToken;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;

public class MakeState extends MakeManager {





    public MakeState(TypeElement clazz, ProcessingEnvironment processingEnv, EnumMakeClass enumMakeClass) {
        super(clazz, processingEnv, enumMakeClass);
    }

    @Override
    public void readImplClass() throws UnsupportedEncodingException, IOException {
        super.readImplClass();
        Elements elements = this.getProcessingEnv().getElementUtils();
        List<ExecutableElement> lElement = ElementFilter.methodsIn(elements.getAllMembers(this.getClazz()));

        for(ExecutableElement el:lElement) {
            AnnToken annToken = el.getAnnotation(AnnToken.class);
            
        }

    }



}
