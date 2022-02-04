package ru.platform.learning.compilerservice.compiler;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

/** Exposes given test source to the compiler. */
public class PlatformSourceFile extends SimpleJavaFileObject {

    private final String content;

    public PlatformSourceFile(String qualifiedClassName, String testSource) {
        super(URI.create(String.format("file://%s%s",
                                       qualifiedClassName.replaceAll("\\.", "/"),
                                       Kind.SOURCE.extension)),
              Kind.SOURCE);
        content = testSource;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return content;
    }
}