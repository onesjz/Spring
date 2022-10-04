package com.one.dao;

import com.one.aop.Logging;
import com.one.model.Guest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GuestDaoImpl implements GuestDao {

    private final List<Guest> guests = new ArrayList<>();

    @Logging
    @Override
    public Guest saveGuest(Guest guest) {
        guests.add(guest);
        return guest;
    }

    @Logging
    @Override
    public Optional<Guest> getGuestById(String id) {
        for (Guest guest : guests) {
            if (guest.getId().equals(id)) {
                return Optional.of(guest);
            }
        }
        return Optional.empty();
    }

    @Logging
    @Override
    public Optional<Guest> getUserByFirstName(String firstName) {
        for (Guest guest : guests) {
            if (guest.getFirstName().equals(firstName)) {
                return Optional.of(guest);
            }
        }
        return Optional.empty();
    }

    @Logging
    @Override
    public List<Guest> getAllGuests() {
        return guests;
    }
}
