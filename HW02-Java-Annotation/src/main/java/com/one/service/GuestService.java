package com.one.service;

import com.one.model.Guest;

import java.util.List;

public interface GuestService {
    Guest saveGuest(String firstName, String lastName);

    void updateScore(String id, int newScore);

    List<Guest> getAllGuests();
}
