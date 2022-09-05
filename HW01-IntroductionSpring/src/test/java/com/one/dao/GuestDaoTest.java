package com.one.dao;

import com.one.model.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("GuestDao should")
class GuestDaoTest {

    private static final String NAME = "name";

    private GuestDao guestDao;

    @BeforeEach
    void setUp() {
        guestDao = new GuestDaoImpl();
    }

    @Test
    @DisplayName("Should save new guest")
    void saveGuest() {
        Guest guest = guestDao.saveGuest(new Guest(NAME));

        assertEquals(NAME, guest.getName());
    }

    @Test
    void getGuestByName() {
        guestDao.saveGuest(new Guest(NAME));
        Guest guest = guestDao.getGuestByName(NAME);

        assertEquals(NAME, guest.getName());
    }

    @Test
    void getAllGuest() {
        guestDao.saveGuest(new Guest(NAME));
        guestDao.saveGuest(new Guest(NAME + "1"));
        guestDao.saveGuest(new Guest(NAME + "2"));

        assertEquals(3, guestDao.getAllGuest().size());
    }
}
