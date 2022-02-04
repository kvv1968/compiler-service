package ru.platform.learning.compilerservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.platform.learning.compilerservice.repository.LogFileRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LogFileServiceTest {
    @InjectMocks
    LogFileService logFileService;
    @Mock
    LogFileRepository fileRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void loadDataBaseLogFile(){
        logFileService.loadDataBaseLogFile();
        System.out.println();

    }
}