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

import impl.ImplReplacements;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class MakeLexerFactory extends MakeManager {

    final String ADD_STATE_DESC = "addStateDesc";

    private MakeLexer makeLexer;
    private List<MakeState> lMakeState;

    public MakeLexerFactory(TypeElement clazz, ProcessingEnvironment processingEnv, EnumMakeClass enumMakeClass) {
        super(clazz, processingEnv, enumMakeClass);
    }

    public void init(MakeLexer makeLexer, List<MakeState> lMakeState) {
        this.makeLexer = makeLexer;
        this.lMakeState = lMakeState;
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

        StringBuilder result = new StringBuilder();
        String replaceToken = getReplacements().getFragment(ADD_STATE_DESC);
        StringBuilder sbToken = new StringBuilder(replaceToken);
        ImplReplacements ir;

        for(MakeState ms:lMakeState) {
            ir = new ImplReplacements(sbToken);
            ir.replaceAll(ms.getEnumMakeClass().getName(), ms.getOutputClassName());
            result.append(ir.getSource());

            message = "Replaced " + ms.getEnumMakeClass().getName() + " with " + ms.getOutputClassName();
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        }

        getReplacements().replaceAllIdentified(ADD_STATE_DESC, result.toString());
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
