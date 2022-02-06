package ru.platform.learning.compilerservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompilerResult {
    private String message;
    private Boolean isResultTask;

}
