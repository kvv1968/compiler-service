package ru.platform.learning.compilerservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.platform.learning.compilerservice.entity.UserTask;
import ru.platform.learning.compilerservice.exception.PlatformCompilerException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class QuestionResolver {

    public UserTask issueResolutionProcess(UserTask userTask) throws PlatformCompilerException {
        String template = userTask.getTemplates().getCorrectAnswers();
        if (template.contains("#")){
            String[] templates = template.split("#");
            if (userTask.getAnswer().contains("#")){
                String[] answers = userTask.getAnswer().split("#");
                return multipleResponseCheck(userTask, templates, answers);

            } else {
                final String msg = "Error wrong task template";
                log.error(msg);
                throw new PlatformCompilerException(msg);
            }
        }
        if (userTask.getAnswer().equals(template)) {
            userTask.setIsResultTask(true);
            userTask.setMessage("Ответы правильные");
            return userTask;
        }
        userTask.setIsResultTask(false);
        userTask.setMessage("Ответы не правильные");
        return userTask;
    }

    private UserTask multipleResponseCheck(UserTask userTask, String[] templates, String[] answers) {
        List<String> result = new LinkedList<>(Arrays.asList(templates));
        result.removeAll(Arrays.asList(answers));
        if (result.isEmpty()){
            userTask.setIsResultTask(true);
            userTask.setMessage("Ответы правильные");
            return userTask;
        }
        userTask.setIsResultTask(false);
        userTask.setMessage("Ответы не правильные");
        return userTask;
    }
}
