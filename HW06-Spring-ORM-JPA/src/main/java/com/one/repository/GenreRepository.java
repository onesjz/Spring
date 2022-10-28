package com.one.repository;

import com.one.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Genre save(Genre genre);

    Genre update(Genre genre);

    Optional<Genre> findById(long id);

    List<Genre> findAll();

    List<Genre> findAllByNames(List<String> names);

    void deleteById(long id);
}
