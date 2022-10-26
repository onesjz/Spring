package com.one.domain;

import java.util.List;

public class Genre {

    private long id;
    private String name;
    private List<Book> books;

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Genre: \n")
            .append("Id -> ").append(id).append("\n")
            .append("Name -> ").append(name).append("\n");

        if (books != null && !books.isEmpty()) {
            builder.append("--- Books ---").append(books);
        }
        builder.append("--- Books End ---");

        return builder.toString();
    }
}
