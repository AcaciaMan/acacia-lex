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

package proc.lexer;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import make.lexer.EnumMakeClass;
import make.lexer.MakeLexer;
import make.lexer.MakeLexerFactory;
import make.lexer.MakeState;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class ProcessAnnLexer extends AbstractProcessor {

    /** public for ServiceLoader */
    public ProcessAnnLexer() {
    }

    @Override
    public boolean process(Set annotations,
            RoundEnvironment roundEnv) {

        String message = "Started annotation processing ... ";
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        try {
            List<MakeState> states = new ArrayList<MakeState>();

            for (Element e : roundEnv.getElementsAnnotatedWith(ann.lexer.AnnState.class)) {
                if (e.getKind().isClass()) {
                    //processState = new ProcessState((TypeElement) e, this.processingEnv);
                    MakeState makeState = new MakeState((TypeElement) e, processingEnv, EnumMakeClass.State);
                    makeState.make();
                    states.add(makeState);
                }
            }

            for (Element e : roundEnv.getElementsAnnotatedWith(ann.lexer.AnnLexer.class)) {
                //procLexer = new ProcessLexer((TypeElement) e, this.processingEnv);
                MakeLexer makeLexer = new MakeLexer((TypeElement) e, this.processingEnv, EnumMakeClass.Lexer);
                makeLexer.make();

                MakeLexerFactory makeLexerFactory  = new MakeLexerFactory((TypeElement) e, processingEnv, EnumMakeClass.LexerFactory);
                makeLexerFactory.init(makeLexer, states);
                makeLexerFactory.make();

            }
        } catch (Exception e) {
        String FatalMessage = "Fatal processing exception " + e.toString();
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, FatalMessage);
        }


        message = "Ended annotation processing ... ";
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);

        return true;
    }

}
