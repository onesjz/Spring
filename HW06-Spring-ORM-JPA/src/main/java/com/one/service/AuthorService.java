package com.one.service;

import com.one.model.Author;

public interface AuthorService {
    Author createAuthor(String name);

    Author checkOrCreateAuthor(String name);

    Author getAuthor(long idAuthor);

    Author getAuthor(String name);

    void removeAuthor(long idAuthor);
}
