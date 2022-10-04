package com.one.service;

import com.one.dao.QuizDao;
import com.one.model.Guest;
import com.one.model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Quiz Service should")
class QuizServiceTest {

    @MockBean
    private GuestService guestService;
    @MockBean
    private IOService ioService;
    @MockBean
    private QuizDao quizDao;
    @MockBean
    private MessageService messageService;
    @Autowired
    private QuizService quizService;

    private Guest guest;
    private Quiz quiz;

    @BeforeEach
    void init() {
        guest = new Guest("FirstName", "LastName");
        quiz = new Quiz("q", Collections.singletonList("a"), 1);
    }

    @Test
    @DisplayName("should to start")
    void startQuiz() {
        doReturn(guest).when(guestService).getUser(anyString());
        doReturn(Collections.singletonList(quiz)).when(quizDao).getQuizListByCSVFile(anyString());

        quizService.startQuiz(guest.getId());

        verify(guestService, times(1)).updateScore(eq(guest.getId()), anyInt());
        verify(quizDao, times(1)).getQuizListByCSVFile(anyString());
        verify(messageService, times(2)).getMessage(anyString());
        verify(ioService, times(2)).printText(anyString());
    }
}
