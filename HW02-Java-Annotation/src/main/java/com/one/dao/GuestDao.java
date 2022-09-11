package com.one.dao;

import com.one.model.Guest;

import java.util.List;

public interface GuestDao {
    Guest saveGuest(Guest guest);

    Guest getGuestById(String id);

    List<Guest> getAllGuests();
}
