package com.one.service;

import com.one.dao.QuizDao;
import com.one.model.Guest;
import com.one.model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Quiz Service should")
class QuizServiceTest {

    private GuestService guestService;
    private ScannerService scannerService;
    private QuizDao quizDao;
    private QuizService quizService;

    @BeforeEach
    void init() {
        guestService = mock(GuestService.class);
        scannerService = mock(ScannerService.class);
        quizDao = mock(QuizDao.class);

        quizService = new QuizServiceImpl(scannerService, quizDao, guestService);
        ReflectionTestUtils.setField(quizService, "csvFile", "file");
    }

    @Test
    @DisplayName("should to start")
    void startQuiz() {
        Guest testGuest = new Guest("FirstName", "LastName");
        Quiz testQuiz = new Quiz("1", Collections.singletonList("2"), 3);
        when(scannerService.readText()).thenReturn("1");
        when(scannerService.readNumbers()).thenReturn(1);
        when(guestService.saveGuest(anyString(), anyString())).thenReturn(testGuest);
        when(quizDao.getQuizListByCSVFile(anyString())).thenReturn(Collections.singletonList(testQuiz));
        when(guestService.getAllGuests()).thenReturn(Collections.singletonList(testGuest));

        quizService.startQuiz();

        verify(guestService, times(1)).saveGuest(anyString(), anyString());
        verify(quizDao, times(1)).getQuizListByCSVFile(anyString());
        verify(scannerService, times(3)).readText();
        verify(scannerService, times(1)).readNumbers();
        verify(scannerService, times(1)).close();
        verify(guestService, times(1)).updateScore(anyString(), anyInt());
        verify(guestService, times(1)).getAllGuests();
    }
}
