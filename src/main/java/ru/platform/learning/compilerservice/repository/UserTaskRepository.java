package ru.platform.learning.compilerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.platform.learning.compilerservice.entity.UserTask;

public interface UserTaskRepository extends JpaRepository<UserTask, Long> {

}
