package ru.platform.learning.compilerservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import ru.platform.learning.compilerservice.compiler.PlatformCompiler;
import ru.platform.learning.compilerservice.compiler.PlatformRunner;
import ru.platform.learning.compilerservice.exception.CompilerException;
import ru.platform.learning.compilerservice.model.CompilerResult;
import ru.platform.learning.compilerservice.model.CompilerTask;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
@Slf4j
public class JavaCompilerService {


    private static final String ROOT_QUALIFIELD_CLASS_NAME = "temp.";


    public CompilerResult startingCompilation(CompilerTask compilerTask) {
        try {
            if (compilerTask == null) {
                final String msg = "Error compilation CompilerResult is null";
                log.error(msg);
                throw new CompilerException(msg);
            }

            String nameClass = compilerTask.getNameClass();
            String source;
            String qualifiedClassName = ROOT_QUALIFIELD_CLASS_NAME + nameClass.toLowerCase() + "." + nameClass;
            String namePackage = "package " + ROOT_QUALIFIELD_CLASS_NAME + nameClass.toLowerCase() + ";\n";

            if (compilerTask.getBytes() == null || compilerTask.getBytes().length == 0){
                source = namePackage + compilerTask.getAnswer();
            } else {
                String template = new String(compilerTask.getBytes(), StandardCharsets.UTF_8);
                source = namePackage + template.replace("$", compilerTask.getAnswer());
            }
            PlatformRunner runner = new PlatformRunner();
            PlatformCompiler compiler = new PlatformCompiler();

            byte[] resultBytes = compiler.compile(qualifiedClassName, source);
            String resultCompilation = new String(resultBytes, StandardCharsets.UTF_8);
            if (resultCompilation.startsWith("Error")){
                return compilerExceptionCompilerResult(resultCompilation);
            }

            String result = processMain(qualifiedClassName, resultBytes, runner);

            return enrichedCompilerResult(compilerTask, result);

        } catch (Throwable ex) {
            return exceptionCompilerResult(ex);
        }
    }

    private CompilerResult compilerExceptionCompilerResult(String result) {
        CompilerResult compilerResult = new CompilerResult();
        compilerResult.setIsResultTask(false);
        compilerResult.setMessage(result);
        return compilerResult;
    }

    private CompilerResult enrichedCompilerResult(CompilerTask compilerTask, String result) {
        String correctAnswers = compilerTask.getCorrectAnswers();
        CompilerResult compilerResult = new CompilerResult();
        if (StringUtils.isEmpty(correctAnswers)){
            compilerResult.setMessage(result);
            compilerResult.setIsResultTask(Objects.equals(result, "Тесты пройдены"));
            return compilerResult;
        }

        if(result.equals(correctAnswers)){
            compilerResult.setIsResultTask(true);
            compilerResult.setMessage("Задача выполнена правильно");
            return compilerResult;
        }
        compilerResult.setIsResultTask(false);
        compilerResult.setMessage("Задача выполнена не правильно");
        return compilerResult;
    }

    private String processMain(String qualifiedClassName, byte[] resultBytes, PlatformRunner runner) throws Throwable {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        runner.run(resultBytes, qualifiedClassName, "main", new Class[] {String[].class}, new Object[]{null});
        return new String(baos.toByteArray(), StandardCharsets.UTF_8).trim().intern();

    }


    private CompilerResult exceptionCompilerResult(Throwable ex) {
        CompilerResult compilerResult = new CompilerResult();
        final String error = ex.getCause().getMessage();
        log.error(ex.getMessage(), ex);
        compilerResult.setIsResultTask(false);
        compilerResult.setMessage(error);
        return compilerResult;
    }

}
