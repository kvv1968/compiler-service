package ru.platform.learning.compilerservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import ru.platform.learning.compilerservice.compiler.PlatformCompiler;
import ru.platform.learning.compilerservice.compiler.PlatformRunner;
import ru.platform.learning.compilerservice.entity.JavaFile;
import ru.platform.learning.compilerservice.entity.UserTask;
import ru.platform.learning.compilerservice.exception.PlatformCompilerException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.jar.JarFile;

@Service
@Slf4j
public class JavaCompilerService {


    private static final String ROOT_QUALIFIELD_CLASS_NAME = "temp.";


    public UserTask startingCompilation(UserTask userTask) {
        try {
            if (userTask == null) {
                final String msg = "Error compilation CompilerResult is null";
                log.error(msg);
                throw new PlatformCompilerException(msg);
            }

            String nameClass = userTask.getTemplates().getNameClass();
            String source;
            String qualifiedClassName = ROOT_QUALIFIELD_CLASS_NAME + nameClass.toLowerCase() + "." + nameClass;
            String namePackage = "package " + ROOT_QUALIFIELD_CLASS_NAME + nameClass.toLowerCase() + ";\n";

            if (userTask.getTemplates().getJavaFile() == null){
                source = namePackage + userTask.getAnswer();
            } else {
                JavaFile jarFile = userTask.getTemplates().getJavaFile();
                String template = new String(jarFile.getBytes(), StandardCharsets.UTF_8);
                source = namePackage + template.replace("$", userTask.getAnswer());
            }
            PlatformRunner runner = new PlatformRunner();
            PlatformCompiler compiler = new PlatformCompiler();

            byte[] resultBytes = compiler.compile(qualifiedClassName, source);
            String resultCompilation = new String(resultBytes, StandardCharsets.UTF_8);
            if (resultCompilation.startsWith("Error")){

                return compilerExceptionUserTask(userTask, resultCompilation);
            }

            String result = processMain(qualifiedClassName, resultBytes, runner);

            return enrichedCompilerResult(userTask, result);

        } catch (Throwable ex) {

            return exceptionUserTask(Objects.requireNonNull(userTask),ex);
        }
    }

    private UserTask compilerExceptionUserTask(UserTask userTask, String result) {
        userTask.setIsResultTask(false);
        userTask.setMessage(result);
        return userTask;
    }

    private UserTask enrichedCompilerResult(UserTask userTask, String result) {
        String correctAnswers = userTask.getTemplates().getCorrectAnswers();
        if (StringUtils.isEmpty(correctAnswers)){
            return updateCompilerResult(userTask, result);
        }

        if(result.equals(correctAnswers)){
            userTask.setIsResultTask(true);
            userTask.setMessage("Задача выполнена правильно");
            return userTask;
        }
        userTask.setIsResultTask(false);
        userTask.setMessage("Задача выполнена не правильно");
        return userTask;
    }

    private UserTask updateCompilerResult(UserTask userTask, String result){
        userTask.setMessage(result);
        userTask.setIsResultTask(Objects.equals(result, "Тесты пройдены"));
        return userTask;
    }


    private String processMain(String qualifiedClassName, byte[] resultBytes, PlatformRunner runner) throws Throwable {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        runner.run(resultBytes, qualifiedClassName, "main", new Class[] {String[].class}, new Object[]{null});
        return new String(baos.toByteArray(), StandardCharsets.UTF_8).trim().intern();

    }


    private UserTask exceptionUserTask(UserTask userTask, Throwable ex) {
        final String error = ex.getCause().getMessage();
        log.error(ex.getMessage(), ex);
        userTask.setIsResultTask(false);
        userTask.setMessage(error);
        return userTask;
    }

}
