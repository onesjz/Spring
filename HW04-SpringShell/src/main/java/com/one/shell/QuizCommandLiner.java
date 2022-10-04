package com.one.shell;

import com.one.model.Guest;
import com.one.service.GuestService;
import com.one.service.IOService;
import com.one.service.MessageService;
import com.one.service.QuizService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class QuizCommandLiner {

    private final QuizService quizService;
    private final GuestService guestService;
    private final MessageService messageService;
    private final IOService ioService;

    private Guest guest;

    public QuizCommandLiner(QuizService quizService, GuestService guestService,
                            MessageService messageService, IOService ioService) {
        this.quizService = quizService;
        this.guestService = guestService;
        this.messageService = messageService;
        this.ioService = ioService;
    }

    @ShellMethod(key = "login", value = "Authorization user")
    public void auth(@ShellOption(value = {"-f", "--first", "--firstName"}, defaultValue = StringUtils.EMPTY) String firstName,
                     @ShellOption(value = {"-l", "--last", "--lastName"}, defaultValue = StringUtils.EMPTY) String lastName) {
        guest = guestService.createUser(firstName, lastName);
        ioService.printText(messageService.getMessage("congratulations", guest.getFirstName()));
    }

    @ShellMethod("Show user info")
    public String info(@ShellOption(value = {"-n", "--name"}) String name) {
        return guestService.getUserInfo(name);
    }

    @ShellMethod(key = {"score", "results", "scoreboard"}, value = "Show scoreboard")
    public void scoreboard() {
        guestService.showAllGuests();
    }

    @ShellMethod("Start quiz")
    @ShellMethodAvailability("security")
    public void start() {
        quizService.startQuiz(guest.getId());
    }

    @ShellMethod(key = "exit", value = "Stop quiz")
    public void stop() {
        ioService.printText(messageService.getMessage("goodbye"));
        System.exit(-1);
    }

    public Availability security() {
        return guest != null ? Availability.available()
            : Availability.unavailable(messageService.getMessage("create.user.login"));
    }
}
