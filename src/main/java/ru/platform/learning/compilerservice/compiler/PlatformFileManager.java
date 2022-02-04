package ru.platform.learning.compilerservice.compiler;

import javax.tools.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/** Adapts {@link PlatformClassFile} to the {@link JavaCompiler} */
public class PlatformFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private final List<PlatformClassFile> compiled = new ArrayList<>();

    public PlatformFileManager(StandardJavaFileManager delegate) {
        super(delegate);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location,
                                               String className,
                                               JavaFileObject.Kind kind,
                                               FileObject sibling)
    {
        PlatformClassFile result = new PlatformClassFile(URI.create("string://" + className));
        compiled.add(result);
        return result;
    }

    /**
     * @return  compiled binaries processed by the current class
     */
    public List<PlatformClassFile> getCompiled() {
        return compiled;
    }
}