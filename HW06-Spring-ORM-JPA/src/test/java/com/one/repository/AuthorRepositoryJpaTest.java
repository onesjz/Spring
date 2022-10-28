package com.one.repository;

import com.one.model.Author;
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
@Import(AuthorRepositoryJpa.class)
@DisplayName("Author repository should")
class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Should to save an author correctly")
    void save() {
        Author actualAuthor = authorRepository.save(new Author("Nick"));
        Author expectedAuthor = entityManager.find(Author.class, actualAuthor.getId());

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("Should to update the author")
    void update() {
        Author author = authorRepository.save(new Author("Nick"));
        author.setName("test");
        Author actualAuthor = authorRepository.update(author);

        assertThat(actualAuthor.getName()).isEqualTo("test");
    }

    @Test
    @DisplayName("Should to find the author by id")
    void findById() {
        Author author = authorRepository.save(new Author("Bob"));
        Optional<Author> actualAuthor = authorRepository.findById(author.getId());

        assertThat(actualAuthor).isPresent()
            .get().extracting("id").isEqualTo(author.getId());
    }

    @Test
    @DisplayName("Should to find the author by name")
    void findByName() {
        Author author = authorRepository.save(new Author("John"));
        Optional<Author> actualAuthor = authorRepository.findByName(author.getName());

        assertThat(actualAuthor).isPresent()
            .get().extracting("name").isEqualTo(author.getName());
    }

    @Test
    @DisplayName("Should to find all authors")
    void findAll() {
        List<Author> authors = authorRepository.findAll();

        assertThat(authors.size()).isGreaterThan(1);
    }

    @Test
    @DisplayName("Should to delete the author by id")
    void deleteById() {
        Author author = authorRepository.save(new Author("John"));
        Author actualAuthor = entityManager.find(Author.class, author.getId());
        authorRepository.deleteById(author.getId());
        Author expectedAuthor = entityManager.find(Author.class, author.getId());

        assertThat(expectedAuthor).isNotEqualTo(actualAuthor);
    }
}
