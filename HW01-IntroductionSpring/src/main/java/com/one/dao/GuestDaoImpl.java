package com.one.dao;

import com.one.model.Guest;

import java.util.ArrayList;
import java.util.List;

public class GuestDaoImpl implements GuestDao {

    private final List<Guest> guests = new ArrayList<>();

    @Override
    public Guest saveGuest(Guest guest) {
        guests.add(guest);
        return guest;
    }

    @Override
    public Guest getGuestByName(String name) {
        for(Guest guest : guests) {
            if(guest.getName().equals(name)) {
                return guest;
            }
        }
        return null;
    }

    @Override
    public List<Guest> getAllGuest() {
        return guests;
    }
}
