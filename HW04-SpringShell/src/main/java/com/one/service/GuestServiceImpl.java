package com.one.service;

import com.one.dao.GuestDao;
import com.one.model.Guest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class GuestServiceImpl implements GuestService {

    private static final Logger log = LoggerFactory.getLogger(GuestServiceImpl.class);

    private final GuestDao guestDao;
    private final MessageService messageService;
    private final IOService ioService;

    public GuestServiceImpl(GuestDao guestDao, MessageService messageService, IOService ioService) {
        this.guestDao = guestDao;
        this.messageService = messageService;
        this.ioService = ioService;
    }

    @Override
    public Guest createUser(String firstName, String lastName) {
        if (firstName.equals(StringUtils.EMPTY) || lastName.equals(StringUtils.EMPTY)) {
            ioService.printText(messageService.getMessage("create.user.name"));
            firstName = ioService.readText();
            ioService.printText(messageService.getMessage("create.user.lastname"));
            lastName = ioService.readText();
        }

        return guestDao.saveGuest(new Guest(firstName, lastName));
    }

    @Override
    public Guest getUser(String id) {
        Optional<Guest> optionalGuest = guestDao.getGuestById(id);

        return optionalGuest.orElseGet(() -> {
            log.warn("The user with id {} is not found!", id);
            return new Guest("Guest", String.format("number%s", new Random().nextInt()));
        });
    }

    @Override
    public String getUserInfo(String name) {
        Optional<Guest> optionalGuest = guestDao.getUserByFirstName(name);

        return optionalGuest.orElseGet(() -> {
            log.warn("The user with name {} is not found!", name);
            return new Guest();
        }).toString();
    }

    @Override
    public void updateScore(String id, int newScore) {
        Optional<Guest> optionalGuest = guestDao.getGuestById(id);
        optionalGuest.ifPresent(g -> g.setScore(newScore));
    }

    @Override
    public void showAllGuests() {
        List<Guest> guests = guestDao.getAllGuests();
        ioService.printText("-------------------");
        String format = "| %1$-15s |\n";

        for (Guest guest : guests) {
            ioService.printFormattedText(format,
                messageService.getMessage("users.result",
                    guest.getFirstName(), guest.getLastName(), String.valueOf(guest.getScore())));
        }
        ioService.printText("-------------------");
    }
}
