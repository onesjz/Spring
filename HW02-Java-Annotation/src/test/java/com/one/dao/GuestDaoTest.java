package com.one.dao;

import com.one.model.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Guest Dao should")
class GuestDaoTest {

    private GuestDao guestDao;
    private Guest guest;

    @BeforeEach
    void init() {
        guestDao = new GuestDaoImpl();
        guest = new Guest("FirstName", "SecondName");
    }

    @Test
    @DisplayName("should save guest correctly")
    void saveGuest() {
        assertEquals(guest, guestDao.saveGuest(guest));
    }

    @Test
    @DisplayName("should get guest by id")
    void getGuestById() {
        guestDao.saveGuest(guest);

        assertEquals(guest, guestDao.getGuestById(guest.getId()));
    }

    @Test
    @DisplayName("should get all guest")
    void getAllGuest() {
        guestDao.saveGuest(guest);
        List<Guest> guests = guestDao.getAllGuests();

        assertFalse(guests.isEmpty());
        assertEquals(guest, guests.get(0));
    }
}
