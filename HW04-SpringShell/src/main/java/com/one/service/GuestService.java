package com.one.service;

import com.one.model.Guest;

public interface GuestService {
    Guest createUser(String firstName, String lastName);

    Guest getUser(String id);

    String getUserInfo(String name);

    void updateScore(String id, int newScore);

    void showAllGuests();
}
