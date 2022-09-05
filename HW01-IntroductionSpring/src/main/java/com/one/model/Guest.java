package com.one.model;

public class Guest {

    private final String name;
    private int score = 0;

    public Guest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return name + " - score: " + score;
    }
}
