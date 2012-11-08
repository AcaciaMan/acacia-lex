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

    final String CHAR_VALUES = "values";
    final String CHAR_VALUE_ARR = "valueArr";
    final String CHAR_VALUE_CHAR = "valueChar";

    final String CHAR_ARR_NUM = "Z";
    final String CHAR_ARR_LENGTH = "L";
    final String CHAR_NUM = "N";
    final String CHAR_CHAR = "C";

    final String NEW_TOKENS = "newTokens";
    final String ADD_TOKEN = "addToken";

    final String TOKEN_TYPE = "SimpleToken.SELECT";
    final String TOKEN_VALUE = "\"SELECTS\"";
    final String TOKEN_METHOD_NUM = "0";


    final String TOKEN_METHODS = "tokenMethods";
    final String TOKEN_METHOD = "tokenMethod";
    final String INSTANCE_CLASS = "instanceClass";

    final String METHOD_CLASS = "SimpleState";
    final String METHOD_NAME = "returnPlus";
    final String METHOD_NUM = "0";

    List<AnnToken> lAnnToken = new ArrayList<AnnToken>();
    List<Integer> lAnnTokenMethodNum = new ArrayList<Integer>();
    List<String> lMethod = new ArrayList<String>();


    public MakeState(TypeElement clazz, ProcessingEnvironment processingEnv, EnumMakeClass enumMakeClass) {
        super(clazz, processingEnv, enumMakeClass);
    }

    @Override
    public void readImplClass() throws UnsupportedEncodingException, IOException {
        super.readImplClass();

        Integer methodNum = 0;

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
                lAnnTokenMethodNum.add(methodNum);
                lMethod.add(el.getSimpleName().toString());
                methodNum++;
            }

            AnnTokens annTokens = el.getAnnotation(AnnTokens.class);
            if (annTokens != null) {
                for (AnnToken a : annTokens.value()) {
                    message = "Found token annotation " + a.type();
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

                    lAnnToken.add(a);
                    lAnnTokenMethodNum.add(methodNum);
                }
                lMethod.add(el.getSimpleName().toString());
                methodNum++;
            }

        }

    }

    @Override
    public void replaceImplParameters() {
        super.replaceImplParameters();

        addCharArrays();
        addTokens();
        addMethods();
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

        Integer i = 0;
        for(AnnToken a:lAnnToken){
            String result = replaceToken;
            result = result.replace(TOKEN_TYPE, a.type());
            result = result.replace(TOKEN_VALUE, "new String(charArray"+i.toString()+")");
            result = result.replace(TOKEN_METHOD_NUM, lAnnTokenMethodNum.get(i).toString());

            sTokens = sTokens + result;
            i++;
        }

        message = "New Tokens " + sTokens;
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        getReplacements().replaceAllIdentified(NEW_TOKENS, sTokens);

    }

    public void addCharArrays() {

        String sValues = " ";

        String message = "Adding Token Values ... ";
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        String replaceValueArr = getReplacements().getFragment(CHAR_VALUE_ARR);
        message = "Found Value Arr " + replaceValueArr;
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        String replaceValueChar = getReplacements().getFragment(CHAR_VALUE_CHAR);
        message = "Found Value Char " + replaceValueChar;
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        String values = getReplacements().getFragment(CHAR_VALUES);
        message = "Found Values " + values;
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        Integer i = 0;
        for(AnnToken a:lAnnToken){
            String result = replaceValueArr;
            result = result.replace(CHAR_ARR_NUM, i.toString());
            result = result.replace(CHAR_ARR_LENGTH, Integer.toString(a.value().length()));

            sValues = sValues + result;

            for (int n = 0; n < a.value().length(); n++) {

            String resultChar = replaceValueChar;
            resultChar = resultChar.replace(CHAR_ARR_NUM, i.toString());
            resultChar = resultChar.replace(CHAR_NUM, Integer.toString(n));
            resultChar = resultChar.replace(CHAR_CHAR, Integer.toString(a.value().charAt(n)));

            sValues = sValues + resultChar;
            }


            i++;
        }

        message = "New Values " + sValues;
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        getReplacements().replaceAllIdentified(CHAR_VALUES, sValues);

    }


    public void addMethods() {

        String sMethods = " ";

        String message = "Adding Methods ... ";
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        String replaceMethod = getReplacements().getFragment(TOKEN_METHOD);
        message = "Found Method " + replaceMethod;
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        String newMethods = getReplacements().getFragment(TOKEN_METHODS);
        message = "Found New Methods " + newMethods;
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        Integer i = 0;
        for(String methodName:lMethod){
            String result = replaceMethod;
            result = result.replace(METHOD_NAME, methodName);
            result = result.replace(METHOD_CLASS, getClassName());
            result = result.replace(METHOD_NUM, i.toString());

            sMethods = sMethods + result;
            i++;
        }

        message = "New Methods " + sMethods;
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        getReplacements().replaceAllIdentified(TOKEN_METHODS, sMethods);

                String instanceClass = getReplacements().getFragment(INSTANCE_CLASS);
        message = "Found New Instance Class " + instanceClass;
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        instanceClass = instanceClass.replace(METHOD_CLASS, getClassName());
        getReplacements().replaceAllIdentified(INSTANCE_CLASS, instanceClass);

    }

}
