package com.one.service;

import com.one.dao.QuizDao;
import com.one.model.Guest;
import com.one.model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("QuizService should")
class QuizServiceTest {

    private GuestService guestService;
    private ScannerService scannerService;
    private QuizDao quizDao;
    private QuizService quizService;

    @BeforeEach
    void setUp() {
        guestService = mock(GuestService.class);
        scannerService = mock(ScannerService.class);
        quizDao = mock(QuizDao.class);

        quizService = new QuizServiceImpl("csv", scannerService, quizDao, guestService);
    }

    @Test
    @DisplayName("Should to start")
    void startQuiz() {
        Guest testGuest = new Guest("Test");
        Quiz testQuiz = new Quiz("1", Collections.singletonList("2"), 3);
        when(scannerService.readText()).thenReturn("1");
        when(scannerService.readNumbers()).thenReturn(1);
        when(guestService.saveGuest(anyString())).thenReturn(testGuest);
        when(quizDao.getQuizListByCSVFile(anyString())).thenReturn(Collections.singletonList(testQuiz));
        when(guestService.getScoreboard()).thenReturn(Collections.singletonList(testGuest));

        quizService.startQuiz();

        verify(guestService, times(1)).saveGuest(anyString());
        verify(quizDao, times(1)).getQuizListByCSVFile(anyString());
        verify(scannerService, times(2)).readText();
        verify(scannerService, times(1)).readNumbers();
        verify(scannerService, times(1)).close();
        verify(guestService, times(1)).updateScore(anyString(), anyInt());
        verify(guestService, times(1)).getScoreboard();
    }
}
