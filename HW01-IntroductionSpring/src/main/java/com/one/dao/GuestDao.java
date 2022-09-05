package com.one.dao;

import com.one.model.Guest;

import java.util.List;

public interface GuestDao {
    Guest saveGuest(Guest guest);
    Guest getGuestByName(String name);
    List<Guest> getAllGuest();
}
