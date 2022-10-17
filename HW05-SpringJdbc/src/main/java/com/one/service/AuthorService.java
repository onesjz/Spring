package com.one.service;

import com.one.domain.Author;

import java.util.List;

public interface AuthorService {
    long createAuthor(String name);

    Author checkAuthorOrCreate(String name);

    Author getAuthor(long id);

    List<Author> getAllAuthors();

    void deleteAuthor(long id);

    void deleteAuthor(String name);
}
