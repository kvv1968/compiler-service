package ru.platform.learning.compilerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.platform.learning.compilerservice.repository.JavaFileRepository;

@SpringBootApplication
public class CompilerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompilerServiceApplication.class, args);
    }

}
