package ru.platform.learning.compilerservice.compiler;

import lombok.Getter;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Getter
public class PlatformCompiler {

    private PlatformSourceFile platformSourceFile;

    public byte[] compile(String qualifiedClassName, String testSource) {
        StringWriter output = new StringWriter();

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        PlatformFileManager fileManager = new PlatformFileManager(compiler.getStandardFileManager(
                null,
                null,
                null
        ));
        PlatformSourceFile platformSourceFile = new PlatformSourceFile(qualifiedClassName, testSource);
        this.platformSourceFile = platformSourceFile;

        List<PlatformSourceFile> compilationUnits = singletonList(platformSourceFile);
        List<String> arguments = new ArrayList<>(asList("-classpath", System.getProperty("java.class.path"),
                "-Xplugin:" + PlatformJavacPlugin.NAME));
        JavaCompiler.CompilationTask task = compiler.getTask(output,
                                                             fileManager,
                                                             null,
                                                             arguments,
                                                             null,
                                                             compilationUnits);
        if (task.call()){
            return fileManager.getCompiled().iterator().next().getCompiledBinaries();
        }
        String result = "Error "+ output;
        return result.getBytes(StandardCharsets.UTF_8);
    }
}
