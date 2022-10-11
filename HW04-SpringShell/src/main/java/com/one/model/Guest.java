package com.one.model;

import java.text.MessageFormat;
import java.util.UUID;

public class Guest {

    private String id;
    private String firstName;
    private String lastName;
    private int score = 0;

    public Guest() {}

    public Guest(String firstName, String lastName) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} {1} -> score: {2}", firstName, lastName, score);
    }
}
