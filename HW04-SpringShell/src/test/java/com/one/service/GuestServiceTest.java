package com.one.service;

import com.one.dao.GuestDao;
import com.one.model.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Guest Service should")
class GuestServiceTest {

    @MockBean
    private GuestDao guestDao;
    @MockBean
    private IOService ioService;
    @MockBean
    private MessageService messageService;
    @Autowired
    private GuestService guestService;
    private Guest guest;

    @BeforeEach
    void init() {
        guest = new Guest("FirstName", "LastName");
    }

    @Test
    @DisplayName("should create new guest correctly")
    void createUser() {
        when(guestDao.saveGuest(any(Guest.class))).thenReturn(guest);

        assertEquals(guest.getId(), guestService.createUser("FirstName", "LastName").getId());

        doReturn("FirstName").when(ioService).readText();
        doReturn("LastName").when(ioService).readText();

        assertEquals(guest.getId(), guestService.createUser("", "").getId());
    }

    @Test
    @DisplayName("should get user")
    void getUser() {
        doReturn(Optional.of(guest)).when(guestDao).getGuestById(guest.getId());

        assertEquals(guest, guestService.getUser(guest.getId()));
        assertEquals("Guest", guestService.getUser("123").getFirstName());
    }

    @Test
    @DisplayName("should get user info")
    void getUserInfo() {
        doReturn(Optional.of(guest)).when(guestDao).getUserByFirstName(guest.getFirstName());

        assertEquals(guest.toString(), guestService.getUserInfo(guest.getFirstName()));
        assertEquals(new Guest().toString(), guestService.getUserInfo("123"));
    }

    @Test
    @DisplayName("should update score")
    void updateScore() {
        when(guestDao.getGuestById(anyString())).thenReturn(Optional.of(guest));
        guestService.updateScore("123", 3);

        assertEquals(3, guest.getScore());
    }

    @Test
    @DisplayName("should get scoreboard")
    void showAllGuests() {
        when(guestDao.getAllGuests()).thenReturn(Collections.singletonList(guest));
        doReturn("test").when(messageService).getMessage(anyString(), anyString(), anyString(), anyString());

        guestService.showAllGuests();

        verify(ioService, times(1)).printFormattedText(anyString(), anyString());
    }
}
