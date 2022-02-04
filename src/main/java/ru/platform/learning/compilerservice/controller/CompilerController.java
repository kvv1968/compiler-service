package ru.platform.learning.compilerservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.learning.compilerservice.dto.TaskData;
import ru.platform.learning.compilerservice.entity.TaskTemplate;
import ru.platform.learning.compilerservice.entity.User;
import ru.platform.learning.compilerservice.entity.UserTask;
import ru.platform.learning.compilerservice.exception.PlatformCompilerException;
import ru.platform.learning.compilerservice.exception.UserTaskException;
import ru.platform.learning.compilerservice.model.ErrorValidation;
import ru.platform.learning.compilerservice.service.*;
import ru.platform.learning.compilerservice.validators.TaskDataValidator;

import java.util.Set;


@RestController
@RequestMapping("/api")
@Slf4j
public class CompilerController {

    @Autowired
    private TaskDataValidator taskDataValidator;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserTaskService userTaskService;
    @Autowired
    private JavaCompilerService compilerService;
    @Autowired
    private QuestionResolver questionResolver;
    @Autowired
    private UserService userService;


    @PostMapping(value = "/comp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> startCompiler(@RequestBody TaskData taskData) throws PlatformCompilerException, UserTaskException {
        if (taskData == null){
            final String msg = "Error taskData is null";
            log.error(msg);
            return ResponseEntity.status(400).body(msg);
        }

       UserTask userTask = createUserTask(taskData);
        if (userTask == null){
            final String msg = "Error userTask is null";
            log.error(msg);
            return ResponseEntity.status(400).body(msg);
        }
        UserTask enriched = null;
        if (userTask.getTemplates().getIsQuestion()){
            enriched = questionResolver.issueResolutionProcess(userTask);
        } else {
            enriched = compilerService.startingCompilation(userTask);
        }

        if (enriched == null){
            final String msg = "Error UserTask enriched is null";
            log.error(msg);
            return ResponseEntity.status(500).body(msg);
        }

        UserTask save = userTaskService.createUserTask(enriched);
        if (save == null){
            final String msg = "Error UserTask save is null";
            log.error(msg);
            return ResponseEntity.status(500).body(msg);
        }
        return ResponseEntity.ok(save.getId());
    }

    public UserTask createUserTask(TaskData taskData) throws UserTaskException {
        User user = userService.findUserById(taskData.getUserId());
        if (user == null){
            final String msg = "Error user is null";
            log.error(msg);
            throw new UserTaskException(msg);
        }
        Set<ErrorValidation> validations = taskDataValidator.validate(taskData, user);
        if(!validations.isEmpty()){
            validations.forEach(error -> {
                log.error(error.getMessage(), error.getNameField());
            });
            throw new UserTaskException("Error create userTask not open");
        }

        TaskTemplate taskTemplate = taskService.findTaskTemplateById(taskData.getIdTask());
        if (taskTemplate == null){
            final String msg = "Error create userTask not open taskTemplate is null";
            log.error(msg);
            throw new UserTaskException(msg);
        }
        UserTask userTask = new UserTask();
        userTask.setAnswer(taskData.getAnswer());
        userTask.setUser(user);
        userTask.setTemplates(taskTemplate);

        return userTask;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerException(Exception ex){
        final String msg = ex.getMessage();
        log.error(msg);
        return ResponseEntity.status(500).body(msg);
    }


}
