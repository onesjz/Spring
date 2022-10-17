package com.one.domain;

import java.util.List;

public class Book {

    private long id;
    private String name;
    private Author author;
    private List<Genre> genres;

    public Book() {
    }

    public Book(String name, Author author, List<Genre> genres) {
        this.name = name;
        this.author = author;
        this.genres = genres;
    }

    public Book(long id, String name, Author author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Book: \n")
            .append("Id -> ").append(id).append("\n")
            .append("Name -> ").append(name).append("\n");

        if (author != null) {
            builder.append(author);
        }

        if (genres != null && !genres.isEmpty()) {
            builder.append("--- Genres ---").append(genres);
        }
        builder.append("--- Genres End ---");

        return builder.toString();
    }
}
