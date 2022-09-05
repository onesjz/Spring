package com.one.service;

import com.one.model.Guest;

import java.util.List;

public interface GuestService {
    Guest saveGuest(String name);
    void updateScore(String name, int newScore);
    List<Guest> getScoreboard();
}
