package com.one.repository;

import com.one.model.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(GenreRepositoryJpa.class)
@DisplayName("Genre repository should")
class GenreRepositoryJpaTest {

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Should to save a genre correclty")
    void save() {
        Genre actualGenre = genreRepository.save(new Genre("genre1"));
        Genre expectedGenre = entityManager.find(Genre.class, actualGenre.getId());

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("Should to update the genre")
    void update() {
        Genre genre = genreRepository.save(new Genre("genre2"));
        genre.setName("test");
        Genre actualGenre = genreRepository.update(genre);

        assertThat(actualGenre.getName()).isEqualTo("test");
    }

    @Test
    @DisplayName("Should to find the genre by id")
    void findById() {
        Genre genre = genreRepository.save(new Genre("genre3"));
        Optional<Genre> actualGenre = genreRepository.findById(genre.getId());

        assertThat(actualGenre).isPresent()
            .get().extracting("id").isEqualTo(genre.getId());
    }

    @Test
    @DisplayName("Should to find all genres")
    void findAll() {
        List<Genre> genres = genreRepository.findAll();

        assertThat(genres.size()).isNotZero();
    }

    @Test
    @DisplayName("Should to find all genres by names")
    void findAllByNames() {
        List<Genre> genres = genreRepository.findAllByNames(List.of("horror"));

        assertThat(genres.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should to delete the genre by id")
    void deleteById() {
        Genre genre = genreRepository.save(new Genre("genre5"));
        Genre actualGenre = entityManager.find(Genre.class, genre.getId());
        genreRepository.deleteById(genre.getId());
        Genre expectedGenre = entityManager.find(Genre.class, genre.getId());

        assertThat(expectedGenre).isNotEqualTo(actualGenre);
    }
}
