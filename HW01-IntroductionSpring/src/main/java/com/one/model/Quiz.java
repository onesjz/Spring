package com.one.model;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;

import java.util.List;

public class Quiz {

    @CsvBindByName(column = "Question", required = true)
    private String question;
    @CsvBindAndSplitByName(column = "Answers", required = true, elementType = String.class, splitOn = "/")
    private List<String> answers;
    @CsvBindByName(column = "Correct", required = true)
    private long correctAnswer;

    public Quiz(String question, List<String> answers, int correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public Quiz() {}

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public long getCorrectAnswer() {
        return correctAnswer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public void setCorrectAnswer(long correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
