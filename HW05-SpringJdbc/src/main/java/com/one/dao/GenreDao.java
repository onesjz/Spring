package com.one.dao;

import com.one.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Optional<Genre> getById(long id);

    Optional<Genre> getByName(String name);

    List<Genre> getAll();

    List<Genre> getAllByNames(List<String> names);

    List<Genre> getAllByBookId(long id);

    long save(Genre genre);

    void deleteById(long id);
}
