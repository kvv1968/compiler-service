package ru.platform.learning.compilerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.platform.learning.compilerservice.entity.TaskTemplate;
import ru.platform.learning.compilerservice.model.LessonTopic;

import java.util.List;


public interface TaskRepository extends JpaRepository<TaskTemplate, Long> {

    List<TaskTemplate>findTaskTemplateByLessonTopic(LessonTopic lessonTopic);

}
