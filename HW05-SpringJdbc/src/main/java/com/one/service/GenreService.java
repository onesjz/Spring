package com.one.service;

import com.one.domain.Genre;

import java.util.List;

public interface GenreService {
    long createGenre(String name);

    List<Genre> updateGenre(List<String> names);

    Genre getGenre(long id);

    List<Genre> getAllGenres();

    List<Genre> getAllGenreForBook(long bookId);

    void deleteGenre(long id);

    void deleteGenre(String name);
}
