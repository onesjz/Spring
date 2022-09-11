package com.one.dao;

import com.one.model.Guest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GuestDaoImpl implements GuestDao {

    private final List<Guest> guests = new ArrayList<>();

    @Override
    public Guest saveGuest(Guest guest) {
        guests.add(guest);
        return guest;
    }

    @Override
    public Guest getGuestById(String id) {
        for (Guest guest : guests) {
            if (guest.getId().equals(id)) {
                return guest;
            }
        }
        return null;
    }

    @Override
    public List<Guest> getAllGuests() {
        return guests;
    }
}
