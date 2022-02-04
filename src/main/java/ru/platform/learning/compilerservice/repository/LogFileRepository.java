package ru.platform.learning.compilerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.platform.learning.compilerservice.entity.LogFile;
import ru.platform.learning.compilerservice.entity.Role;


public interface LogFileRepository extends JpaRepository<LogFile, Long> {




}
