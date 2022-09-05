package com.one.service;

import com.one.dao.GuestDao;
import com.one.model.Guest;

import java.util.List;

public class GuestServiceImpl implements GuestService {

    private final GuestDao guestDao;

    public GuestServiceImpl(GuestDao guestDao) {
        this.guestDao = guestDao;
    }

    @Override
    public Guest saveGuest(String name) {
        return guestDao.saveGuest(new Guest(name));
    }

    @Override
    public void updateScore(String name, int newScore) {
        Guest guest = guestDao.getGuestByName(name);
        guest.setScore(newScore);
    }

    @Override
    public List<Guest> getScoreboard() {
        return guestDao.getAllGuest();
    }
}
