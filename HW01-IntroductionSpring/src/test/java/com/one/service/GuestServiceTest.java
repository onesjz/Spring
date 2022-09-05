package com.one.service;

import com.one.dao.GuestDao;
import com.one.model.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("GuestService should")
class GuestServiceTest {

    private GuestService guestService;
    private GuestDao guestDao;

    @BeforeEach
    void setUp() {
        guestDao = Mockito.mock(GuestDao.class);
        guestService = new GuestServiceImpl(guestDao);
    }

    @Test
    @DisplayName("Should save the new guest")
    void saveGuest() {
        Guest guest = new Guest("Test");
        when(guestDao.saveGuest(any(Guest.class))).thenReturn(guest);

        assertEquals(guest, guestService.saveGuest("Test"));
        verify(guestDao, times(1)).saveGuest(any(Guest.class));
    }

    @Test
    @DisplayName("Should update the score")
    void updateScore() {
        Guest guest = new Guest("Test");
        when(guestDao.getGuestByName(anyString())).thenReturn(guest);

        guestService.updateScore("Test", 5);

        assertEquals(5, guest.getScore());
        verify(guestDao, times(1)).getGuestByName(anyString());
    }

    @Test
    @DisplayName("should get the current scoreboard")
    void getScoreboard() {
        List<Guest> guests = List.of(new Guest("Test1"), new Guest("Test2"));
        when(guestDao.getAllGuest()).thenReturn(guests);

        assertEquals(2, guestService.getScoreboard().size());
        verify(guestDao, times(1)).getAllGuest();
    }
}
