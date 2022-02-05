package ru.platform.learning.compilerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.platform.learning.compilerservice.entity.TaskTemplate;
import ru.platform.learning.compilerservice.model.LessonTopic;
import ru.platform.learning.compilerservice.repository.TaskRepository;

import java.util.List;

@Service
@Transactional
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;


    public TaskTemplate findTaskTemplateById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }


}
