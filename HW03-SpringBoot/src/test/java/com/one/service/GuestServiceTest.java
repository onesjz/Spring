package com.one.service;

import com.one.dao.GuestDao;
import com.one.model.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Guest Service should")
class GuestServiceTest {

    @Mock
    private GuestDao guestDao;
    private GuestService guestService;
    private Guest guest;

    @BeforeEach
    void init() {
        guestService = new GuestServiceImpl(guestDao);
        guest = new Guest("FirstName", "LastName");
    }

    @Test
    @DisplayName("should save new guest")
    void saveGuest() {
        when(guestDao.saveGuest(any(Guest.class))).thenReturn(guest);

        assertEquals(guest.getId(), guestService.saveGuest("FirstName", "LastName").getId());
    }

    @Test
    @DisplayName("should update score")
    void updateScore() {
        when(guestDao.getGuestById(anyString())).thenReturn(guest);
        guestService.updateScore("123", 3);

        assertEquals(3, guest.getScore());
    }

    @Test
    @DisplayName("should get scoreboard")
    void getScoreboard() {
        when(guestDao.getAllGuests()).thenReturn(Collections.singletonList(guest));

        assertFalse(guestService.getAllGuests().isEmpty());
    }
}
