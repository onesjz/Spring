package com.one.service;

import com.one.dao.QuizDao;
import com.one.model.Guest;
import com.one.model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Quiz Service should")
class QuizServiceTest {

    @Mock
    private GuestService guestService;
    @Mock
    private IOService ioService;
    @Mock
    private QuizDao quizDao;
    @Mock
    private MessageService messageService;
    private QuizService quizService;

    @BeforeEach
    void init() {
        quizService = new QuizServiceImpl(messageService, ioService, quizDao, guestService, "path");
    }

    @Test
    @DisplayName("should to start")
    void startQuiz() {
        Guest testGuest = new Guest("FirstName", "LastName");
        Quiz testQuiz = new Quiz("1", Collections.singletonList("2"), 3);
        when(ioService.readText()).thenReturn("1");
        when(ioService.readNumbers()).thenReturn(1);
        when(guestService.saveGuest(anyString(), anyString())).thenReturn(testGuest);
        when(quizDao.getQuizListByCSVFile(anyString())).thenReturn(Collections.singletonList(testQuiz));
        when(guestService.getAllGuests()).thenReturn(Collections.singletonList(testGuest));
        doReturn("text").when(messageService).getMessage(anyString());
        doReturn("text").when(messageService).getMessage(anyString(), anyString());
        doReturn("text").when(messageService).getMessage(anyString(), anyString(), anyString(), anyString());

        quizService.startQuiz();

        verify(guestService, times(1)).saveGuest(anyString(), anyString());
        verify(quizDao, times(1)).getQuizListByCSVFile(anyString());
        verify(ioService, times(3)).readText();
        verify(ioService, times(1)).readNumbers();
        verify(ioService, times(13)).printText(anyString());
        verify(ioService, times(1)).printFormattedText(anyString(), anyString());
        verify(ioService, times(1)).close();
        verify(guestService, times(1)).updateScore(anyString(), anyInt());
        verify(guestService, times(1)).getAllGuests();
    }
}
