package ru.platform.learning.compilerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskData {

    private Long idTask;
    private String answer;
    private Long userId;
}
