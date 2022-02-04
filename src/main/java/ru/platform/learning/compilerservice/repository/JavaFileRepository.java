package ru.platform.learning.compilerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.platform.learning.compilerservice.entity.JavaFile;
import ru.platform.learning.compilerservice.entity.Role;

import java.util.jar.JarFile;


public interface JavaFileRepository extends JpaRepository<JavaFile, Long> {



}
