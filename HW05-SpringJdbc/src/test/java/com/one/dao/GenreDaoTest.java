package com.one.dao;

import com.one.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import(GenreDaoJdbc.class)
@DisplayName("Genre dao should")
class GenreDaoTest {

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("Should to get the genre by id")
    void getById() {
        Optional<Genre> genre = genreDao.getById(1);

        assertTrue(genre.isPresent());
        assertEquals("horror", genre.get().getName());
    }

    @Test
    @DisplayName("Should to get the genre by name")
    void getByName() {
        Optional<Genre> genre = genreDao.getByName("horror");

        assertTrue(genre.isPresent());
        assertEquals(1, genre.get().getId());
    }

    @Test
    @DisplayName("Should to get all genres")
    void getAll() {
        List<Genre> genres = genreDao.getAll();

        assertFalse(genres.isEmpty());
    }

    @Test
    @DisplayName("Should to get all genres by names")
    void getAllByNames() {
        List<Genre> genres = genreDao.getAllByNames(Collections.singletonList("horror"));

        assertFalse(genres.isEmpty());
    }

    @Test
    @DisplayName("Should to get all genres by book id")
    void getAllByBookId() {
        List<Genre> genres = genreDao.getAllByBookId(1);

        assertFalse(genres.isEmpty());
    }

    @Test
    @DisplayName("Should to save a new genre")
    void save() {
        Genre expectedGenre = new Genre("Novel");
        long id = genreDao.save(expectedGenre);
        Optional<Genre> actualGenre = genreDao.getById(id);

        assertTrue(actualGenre.isPresent());
        assertEquals(expectedGenre.getName(), actualGenre.get().getName());
    }

    @Test
    @DisplayName("Should to delete the genre by id")
    void deleteById() {
        Genre expectedGenre = new Genre("Adventure");
        long id = genreDao.save(expectedGenre);
        genreDao.deleteById(id);
        Optional<Genre> actualGenre = genreDao.getById(id);

        assertFalse(actualGenre.isPresent());
    }
}
