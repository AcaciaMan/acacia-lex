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
import impl.ImplUtility;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class MakeManager {

    final static String JAVA_CLASS_NAME_EXT = ".java";
    final static String IMPL = "Impl";
    final static String FACTORY = "Factory";
    final static String PACKAGE = "package ";
    final static String LEXER_IMPL_PACKAGE_NAME = "impl\\.lexer";
    final static String GETTER_PREFIX = "get";
    private InputStream inputStream;
    private StringBuilder inputSequence;
    private CharSequence classPackageName;
    private CharSequence className;
    private CharSequence classFileName;
    private TypeElement clazz;
    public ProcessingEnvironment processingEnv;
    private EnumMakeClass enumMakeClass;
    private ImplReplacements replacements;

    public MakeManager(TypeElement clazz, ProcessingEnvironment processingEnv, EnumMakeClass enumMakeClass) {
        this.clazz = clazz;
        this.processingEnv = processingEnv;
        this.enumMakeClass = enumMakeClass;

    }

    public void make() throws IOException {
        setClassParams();
        readImplClass();
        replaceImplParameters();
        writeToFile();

    }

    public void setClassParams() {

        PackageElement packageElement = (PackageElement) clazz.getEnclosingElement();
        classPackageName = packageElement.getQualifiedName();
        className = clazz.getSimpleName();
        classFileName = clazz.getQualifiedName();

    }

    public void readImplClass() throws UnsupportedEncodingException, IOException {

        inputStream = impl.lexer.ImplLexer.class.getResourceAsStream(getInputFileName());
        Reader reader = new InputStreamReader(inputStream, "UTF-8");

        ImplUtility utility = new ImplUtility();
        inputSequence = utility.getCharSequenceFromReader(reader);
        replacements = new ImplReplacements(inputSequence);

    }

    public void writeToFile() throws IOException {
        Writer w = null;
        try {
            JavaFileObject f = processingEnv.getFiler().createSourceFile(getOutputClassName());
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Creating " + f.toUri());
            w = f.openWriter();
            w.append(inputSequence);
            w.flush();
        } finally {
            if (w != null) {
                w.close();
            }
        }
    }

    public String getInputFileName() {
        return enumMakeClass.getName() + JAVA_CLASS_NAME_EXT;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public CharSequence getClassPackageName() {
        return classPackageName;
    }

    public void setClassPackageName(CharSequence classPackageName) {
        this.classPackageName = classPackageName;
    }

    public CharSequence getClassName() {
        return className;
    }

    public void setClassName(CharSequence className) {
        this.className = className;
    }

    public CharSequence getClassFileName() {
        return classFileName;
    }

    public void setClassFileName(CharSequence classFileName) {
        this.classFileName = classFileName;
    }

    public void replaceImplParameters() {
        replacements.replaceAll(PACKAGE+LEXER_IMPL_PACKAGE_NAME, PACKAGE + getClassPackageName().toString());
        replacements.replaceAll(enumMakeClass.getName(), getOutputClassName());
    }

    public StringBuilder getInputSequence() {
        return inputSequence;
    }

    public void setInputSequence(StringBuilder inputSequence) {
        this.inputSequence = inputSequence;
    }

    public String getOutputClassName() {
        return className.toString() + enumMakeClass.getExt();
    }

    /**
     * @return the clazz
     */
    public TypeElement getClazz() {
        return clazz;
    }

    /**
     * @param clazz the clazz to set
     */
    public void setClazz(TypeElement clazz) {
        this.clazz = clazz;
    }

    /**
     * @return the processingEnv
     */
    public ProcessingEnvironment getProcessingEnv() {
        return processingEnv;
    }

    /**
     * @param processingEnv the processingEnv to set
     */
    public void setProcessingEnv(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    /**
     * @return the enumMakeClass
     */
    public EnumMakeClass getEnumMakeClass() {
        return enumMakeClass;
    }

    /**
     * @param enumMakeClass the enumMakeClass to set
     */
    public void setEnumMakeClass(EnumMakeClass enumMakeClass) {
        this.enumMakeClass = enumMakeClass;
    }

    /**
     * @return the replacements
     */
    public ImplReplacements getReplacements() {
        return replacements;
    }

    /**
     * @param replacements the replacements to set
     */
    public void setReplacements(ImplReplacements replacements) {
        this.replacements = replacements;
    }

}
