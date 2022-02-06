package ru.platform.learning.compilerservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogFileServiceTest {
    @InjectMocks
    LogService logFileService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void loadDataBaseLogFile(){

        System.out.println();

    }
}