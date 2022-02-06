package ru.platform.learning.compilerservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.platform.learning.compilerservice.exception.CompilerException;
import ru.platform.learning.compilerservice.model.CompilerResult;
import ru.platform.learning.compilerservice.model.CompilerTask;
import ru.platform.learning.compilerservice.service.*;


@RestController
@RequestMapping("/api/comp")
@Slf4j
public class CompilerController {

    private final JavaCompilerService compilerService;
    private final QuestionResolver questionResolver;
    private final LogService logService;

    @Autowired
    public CompilerController(JavaCompilerService compilerService, QuestionResolver questionResolver, LogService logService) {
        this.compilerService = compilerService;
        this.questionResolver = questionResolver;
        this.logService = logService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> startCompiler(@RequestBody CompilerTask compilerTask) throws CompilerException {
        if (compilerTask == null){
            final String msg = "Error compilerTask is null";
            log.error(msg);
            return ResponseEntity.status(400).body(msg);
        }
        CompilerResult result;
        if (compilerTask.getIsQuestion()){

            result = questionResolver.issueResolutionProcess(compilerTask);

        } else {

            result = compilerService.startingCompilation(compilerTask);

        }
        if (result == null){
            final String msg = "Error compilerResult is null";
            log.error(msg);
            return ResponseEntity.status(500).body(msg);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/file.zip")
    public byte[] getCurrentLog(){
        return logService.getCurrentLogFile();
    }

    @GetMapping("/files.zip")
    public byte[] getArchivedLog(){
        return logService.getArchivedDir();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerException(Exception ex){
        final String msg = ex.getMessage();
        log.error(msg);
        return ResponseEntity.status(500).body(msg);
    }


}
