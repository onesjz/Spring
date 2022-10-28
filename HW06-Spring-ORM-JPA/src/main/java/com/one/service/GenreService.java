package com.one.service;

import com.one.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> checkOrUpdateGenre(List<String> names);

    List<Genre> getAllGenreByNames(List<String> nameGenres);
}
