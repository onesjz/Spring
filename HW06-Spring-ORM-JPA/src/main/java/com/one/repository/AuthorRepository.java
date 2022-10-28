package com.one.repository;

import com.one.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Author save(Author author);

    Author update(Author author);

    Optional<Author> findById(long id);

    Optional<Author> findByName(String name);

    List<Author> findAll();

    void deleteById(long id);
}
