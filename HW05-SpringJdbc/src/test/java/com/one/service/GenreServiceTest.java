package com.one.service;

import com.one.dao.GenreDao;
import com.one.domain.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Genre service should")
class GenreServiceTest {

    @Mock
    private GenreDao genreDao;

    private GenreService genreService;

    @BeforeEach
    void init() {
        genreService = new GenreServiceImpl(genreDao);
    }

    @Test
    @DisplayName("Should to create the genre")
    void createGenre() {
        doReturn(1L).when(genreDao).save(any(Genre.class));

        assertEquals(1, genreService.createGenre("name"));
    }

    @Test
    @DisplayName("Should to update genres")
    void updateGenre() {
        doReturn(Collections.emptyList()).when(genreDao).getAllByNames(Collections.emptyList());
        doReturn(Collections.singletonList(new Genre("name")))
            .when(genreDao).getAllByNames(Collections.singletonList("name"));
        doReturn(Collections.singletonList(new Genre("name")))
            .when(genreDao).getAllByNames(Collections.singletonList("test"));

        genreService.updateGenre(Collections.emptyList());
        genreService.updateGenre(Collections.singletonList("name"));
        genreService.updateGenre(Collections.singletonList("test"));

        verify(genreDao, times(1)).save(any(Genre.class));
        verify(genreDao, times(4)).getAllByNames(anyList());
    }

    @Test
    @DisplayName("Should to get the genre")
    void getGenre() {
        doReturn(Optional.of(new Genre("name"))).when(genreDao).getById(1);
        Genre expectedGenre = genreService.getGenre(1);

        assertEquals("name", expectedGenre.getName());
    }

    @Test
    @DisplayName("Should to return all genres")
    void getAllGenres() {
        doReturn(Collections.singletonList(new Genre())).when(genreDao).getAll();
        List<Genre> genres = genreService.getAllGenres();

        assertFalse(genres.isEmpty());
    }

    @Test
    @DisplayName("Should to get all genres for book")
    void getAllGenreForBook() {
        doReturn(Collections.singletonList(new Genre())).when(genreDao).getAllByBookId(1);
        List<Genre> genres = genreService.getAllGenreForBook(1);

        assertFalse(genres.isEmpty());
    }

    @Test
    @DisplayName("Should to delete genre by id")
    void deleteGenreById() {
        genreService.deleteGenre(1);

        verify(genreDao, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Should to delete genre by name")
    void deleteGenreByName() {
        doReturn(Optional.of(new Genre())).when(genreDao).getByName(anyString());
        genreService.deleteGenre("name");

        verify(genreDao, times(1)).getByName(anyString());
        verify(genreDao, times(1)).deleteById(anyLong());
    }
}
