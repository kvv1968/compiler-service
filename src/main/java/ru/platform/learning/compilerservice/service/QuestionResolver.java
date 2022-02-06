package ru.platform.learning.compilerservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.platform.learning.compilerservice.exception.CompilerException;
import ru.platform.learning.compilerservice.model.CompilerResult;
import ru.platform.learning.compilerservice.model.CompilerTask;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class QuestionResolver {

    public CompilerResult issueResolutionProcess(CompilerTask compilerTask) throws CompilerException {
        String template = compilerTask.getCorrectAnswers();
        if (template.contains("#")){
            String[] templates = template.split("#");
            if (compilerTask.getAnswer().contains("#")){
                String[] answers = compilerTask.getAnswer().split("#");
                return multipleResponseCheck(templates, answers);

            } else {
                final String msg = "Error wrong task template";
                log.error(msg);
                throw new CompilerException(msg);
            }
        }
        CompilerResult compilerResult = new CompilerResult();
        if (compilerTask.getAnswer().equals(template)) {
            compilerResult.setIsResultTask(true);
            compilerResult.setMessage("Ответы правильные");
            return compilerResult;
        }
        compilerResult.setIsResultTask(false);
        compilerResult.setMessage("Ответы не правильные");
        return compilerResult;
    }

    private CompilerResult multipleResponseCheck(String[] templates, String[] answers) {
        List<String> result = new LinkedList<>(Arrays.asList(templates));
        result.removeAll(Arrays.asList(answers));
        CompilerResult compilerResult = new CompilerResult();
        if (result.isEmpty()){
            compilerResult.setIsResultTask(true);
            compilerResult.setMessage("Ответы правильные");
            return compilerResult;
        }
        compilerResult.setIsResultTask(false);
        compilerResult.setMessage("Ответы не правильные");
        return compilerResult;
    }
}
