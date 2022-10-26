package com.one.dao;

import com.one.domain.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import(AuthorDaoJdbc.class)
@DisplayName("Author dao should")
class AuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("Should to get the author by id")
    void getById() {
        Optional<Author> author = authorDao.getById(1);

        assertTrue(author.isPresent());
        assertEquals("author #1", author.get().getName());
    }

    @Test
    @DisplayName("Should to get the author by name")
    void getByName() {
        Optional<Author> author = authorDao.getByName("author #1");

        assertTrue(author.isPresent());
        assertEquals(1, author.get().getId());
    }

    @Test
    @DisplayName("Should to get all authors")
    void getAll() {
        List<Author> authors = authorDao.getAll();

        assertFalse(authors.isEmpty());
    }

    @Test
    @DisplayName("Should to save a new author")
    void save() {
        Author expectedAuthor = new Author("Bob");
        long id = authorDao.save(expectedAuthor);
        Optional<Author> actualAuthor = authorDao.getById(id);

        assertTrue(actualAuthor.isPresent());
        assertEquals(expectedAuthor.getName(), actualAuthor.get().getName());
    }

    @Test
    @DisplayName("Should to delete the author")
    void deleteById() {
        Author expectedAuthor = new Author("John");
        long id = authorDao.save(expectedAuthor);
        authorDao.deleteById(id);
        Optional<Author> actualAuthor = authorDao.getById(id);

        assertFalse(actualAuthor.isPresent());
    }
}
