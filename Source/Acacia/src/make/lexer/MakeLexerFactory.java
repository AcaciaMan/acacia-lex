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

import ann.lexer.AnnStartState;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;


public class MakeLexerFactory extends MakeManager {

    final String ADD_STATE_DESC = "addStateDesc";

    final String FACTORY_FROM_STATE = "ImplState.class";
    final String FACTORY_TO_STATE = "Impl.class";

    private MakeLexer makeLexer;
    private List<MakeState> lMakeState;

    List<String> lStartState = new ArrayList<String>();

    public MakeLexerFactory(TypeElement clazz, ProcessingEnvironment processingEnv, EnumMakeClass enumMakeClass) {
        super(clazz, processingEnv, enumMakeClass);
    }

    public void init(MakeLexer makeLexer, List<MakeState> lMakeState) {
        this.makeLexer = makeLexer;
        this.lMakeState = lMakeState;
    }

        @Override
    public void readImplClass() throws UnsupportedEncodingException, IOException {
        super.readImplClass();

        Elements elements = this.getProcessingEnv().getElementUtils();
        List<VariableElement> lElement = ElementFilter.fieldsIn(elements.getAllMembers(this.getClazz()));

        for (VariableElement el : lElement) {
            String message = "Found State annotation " + el.getSimpleName();
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);


            AnnStartState annStartState = el.getAnnotation(AnnStartState.class);
            if (annStartState != null) {
                message = "Found start state annotation " + el.asType();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

                lStartState.add(el.asType().toString());
            }

        }

    }

    @Override
    public void replaceImplParameters() {
        super.replaceImplParameters();

        getReplacements().replaceAll(makeLexer.getEnumMakeClass().getName(), makeLexer.getOutputClassName());
        addStateDesc();
    }


    public void addStateDesc() {

        String message = "Adding start StateDesc ... ";
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);


        String replaceStartState = getReplacements().getFragment(ADD_STATE_DESC);

        String startStates = " ";
        String result = " ";

        for(String s:lStartState) {
            result = replaceStartState;
            result = result.replace(FACTORY_FROM_STATE, s+FACTORY_TO_STATE);

            startStates = startStates + result;
        }

        getReplacements().replaceAllIdentified(ADD_STATE_DESC, startStates);
    }

    /**
     * @return the lMakeState
     */
    public List<MakeState> getlMakeState() {
        return lMakeState;
    }

    /**
     * @param lMakeState the lMakeState to set
     */
    public void setlMakeState(List<MakeState> lMakeState) {
        this.lMakeState = lMakeState;
    }

}
