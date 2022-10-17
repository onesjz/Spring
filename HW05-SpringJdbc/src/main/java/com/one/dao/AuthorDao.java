package com.one.dao;

import com.one.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Optional<Author> getById(long id);

    Optional<Author> getByName(String name);

    List<Author> getAll();

    long save(Author author);

    void deleteById(long id);
}
