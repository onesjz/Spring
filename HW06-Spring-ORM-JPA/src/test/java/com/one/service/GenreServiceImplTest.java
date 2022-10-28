package com.one.service;

import com.one.model.Genre;
import com.one.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Genre service should")
class GenreServiceImplTest {

    @Mock
    private GenreRepository genreRepository;

    private GenreService genreService;

    @BeforeEach
    void init() {
        genreService = new GenreServiceImpl(genreRepository);
    }

    @Test
    @DisplayName("Should to try get genres or create")
    void checkOrUpdateGenre() {
        doReturn(List.of(new Genre("1"), new Genre("2")))
            .when(genreRepository).findAllByNames(List.of("1", "2"));
        List<Genre> genres = genreService.checkOrUpdateGenre(List.of("1", "2"));

        assertEquals(2, genres.size());
        verify(genreRepository, never()).save(any(Genre.class));
        verify(genreRepository, times(1)).findAllByNames(anyList());
    }

    @Test
    @DisplayName("Should to get genres by list of names")
    void getAllGenreByNames() {
        doReturn(List.of(new Genre())).when(genreRepository).findAllByNames(anyList());
        List<Genre> genres = genreService.getAllGenreByNames(List.of("1"));

        assertEquals(1, genres.size());
        verify(genreRepository, times(1)).findAllByNames(anyList());
    }
}
