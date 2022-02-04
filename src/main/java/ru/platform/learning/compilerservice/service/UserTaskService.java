package ru.platform.learning.compilerservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.platform.learning.compilerservice.entity.UserTask;
import ru.platform.learning.compilerservice.repository.UserTaskRepository;


@Service
@Slf4j
public class UserTaskService {

    @Autowired
    private UserTaskRepository userTaskRepository;

    public UserTask createUserTask(UserTask userTask){
        if (userTask == null){
            final String msg = "Error userTask is null";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        return userTaskRepository.save(userTask);
    }


}
