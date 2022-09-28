package com.one.service;

import com.one.dao.GuestDao;
import com.one.model.Guest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestDao guestDao;

    public GuestServiceImpl(GuestDao guestDao) {
        this.guestDao = guestDao;
    }

    @Override
    public Guest saveGuest(String firstName, String lastName) {
        return guestDao.saveGuest(new Guest(firstName, lastName));
    }

    @Override
    public void updateScore(String id, int newScore) {
        Guest guest = guestDao.getGuestById(id);
        guest.setScore(newScore);
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestDao.getAllGuests();
    }
}
