package com.one.dao;

import com.one.model.Guest;

import java.util.List;
import java.util.Optional;

public interface GuestDao {
    Guest saveGuest(Guest guest);

    Optional<Guest> getGuestById(String id);

    Optional<Guest> getUserByFirstName(String firstName);

    List<Guest> getAllGuests();
}
