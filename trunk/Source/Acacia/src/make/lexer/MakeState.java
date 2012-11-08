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
import ann.lexer.AnnTokens;
import impl.ImplReplacements;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

public class MakeState extends MakeManager {

    final String NEW_TOKENS = "newTokens";
    final String ADD_TOKEN = "addToken";

    final String TOKEN_TYPE = "SimpleToken.SELECT";
    final String TOKEN_VALUE = "SELECTS";


    List<AnnToken> lAnnToken = new ArrayList<AnnToken>();

    public MakeState(TypeElement clazz, ProcessingEnvironment processingEnv, EnumMakeClass enumMakeClass) {
        super(clazz, processingEnv, enumMakeClass);
    }

    @Override
    public void readImplClass() throws UnsupportedEncodingException, IOException {
        super.readImplClass();
        Elements elements = this.getProcessingEnv().getElementUtils();
        List<ExecutableElement> lElement = ElementFilter.methodsIn(elements.getAllMembers(this.getClazz()));

        for (ExecutableElement el : lElement) {
            String message = "Found State annotation " + el.getSimpleName();
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);


            AnnToken annToken = el.getAnnotation(AnnToken.class);
            if (annToken != null) {
                message = "Found token annotation " + annToken.type();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

                lAnnToken.add(annToken);
            }

            AnnTokens annTokens = el.getAnnotation(AnnTokens.class);
            if (annTokens != null) {
                for (AnnToken a : annTokens.value()) {
                    message = "Found token annotation " + a.type();
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

                    lAnnToken.add(a);
                }
            }

        }

    }

    @Override
    public void replaceImplParameters() {
        super.replaceImplParameters();

        addTokens();
    }

    public void addTokens() {

        String sTokens = " ";

        String message = "Adding Tokens ... ";
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        String replaceToken = getReplacements().getFragment(ADD_TOKEN);
        message = "Found Token " + replaceToken;
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        String newTokens = getReplacements().getFragment(NEW_TOKENS);
        message = "Found New Tokens " + newTokens;
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        for(AnnToken a:lAnnToken){
            String result = replaceToken;
            result = result.replace(TOKEN_TYPE, a.type());

            String sValue = a.value();
            sValue = sValue.replace("\\", "\\\\");
            result = result.replace(TOKEN_VALUE, sValue);

            sTokens = sTokens + result;
        }

        message = "New Tokens " + sTokens;
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        getReplacements().replaceAllIdentified(NEW_TOKENS, sTokens);

    }



}
