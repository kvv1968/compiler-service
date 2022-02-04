package ru.platform.learning.compilerservice.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.ToOne;
import ru.platform.learning.compilerservice.model.LessonTopic;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tasks_templates", uniqueConstraints =
        {
                @UniqueConstraint(columnNames = "id"),
                @UniqueConstraint(columnNames = "nameTask")
        }
)
@JsonIgnoreProperties("userTasks")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskTemplate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LessonTopic lessonTopic;

    private String nameTask;

    @Column(length = 1024)
    private String description;

    private String nameClass;

    @Column(length = 3000)
    private String res;

    private Boolean  isQuestion;

    @Column(length = 1024)
    private String correctAnswers;

    @Transient
    @OneToOne(mappedBy = "taskTemplate", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private JavaFile javaFile;

    @Transient
    @OneToMany(mappedBy="templates", fetch = FetchType.EAGER)
    private Set<UserTask> userTasks;

}


