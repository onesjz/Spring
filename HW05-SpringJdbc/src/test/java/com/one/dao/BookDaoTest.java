package com.one.dao;

import com.one.domain.Author;
import com.one.domain.Book;
import com.one.domain.Genre;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
@DisplayName("Book dao should")
class BookDaoTest {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private GenreDao genreDao;
    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("Should to get the book by id")
    void getById() {
        Optional<Book> book = bookDao.getById(1);

        assertTrue(book.isPresent());
        assertEquals("book #1", book.get().getName());
    }

    @Test
    @DisplayName("Should to get the book by name")
    void getByName() {
        Optional<Book> book = bookDao.getByName("book #1");

        assertTrue(book.isPresent());
        assertEquals(1, book.get().getId());
    }

    @Test
    @DisplayName("Should to get all books")
    void getAll() {
        List<Book> books = bookDao.getAll();

        assertFalse(books.isEmpty());
    }

    @Test
    @DisplayName("Should to get all books by genre id")
    void getAllByGenreId() {
        List<Book> books = bookDao.getAllByGenreId(1);

        assertFalse(books.isEmpty());
        assertEquals(2, books.size());
    }

    @Test
    @DisplayName("Should to get all book by author id")
    void getByAuthorId() {
        List<Book> books = bookDao.getByAuthorId(1);

        assertFalse(books.isEmpty());
        assertEquals(2, books.size());
    }

    @Test
    @DisplayName("Should to save a new book")
    void save() {
        Author author = new Author("author #2");
        author.setId(2);
        Genre genre = new Genre("horror");
        genre.setId(1);
        Book expectedBook = new Book("Book Test",
            author, Collections.singletonList(genre));
        long id = bookDao.save(expectedBook);
        Optional<Book> actualBook = bookDao.getById(id);

        assertTrue(actualBook.isPresent());
        assertEquals(expectedBook.getName(), actualBook.get().getName());
    }

    @Test
    @DisplayName("Should to delete the book by id")
    void deleteById() {
        Author author = new Author("author #2");
        author.setId(2);
        Genre genre = new Genre("horror");
        genre.setId(1);
        Book expectedBook = new Book("Book delete #1",
            author, Collections.singletonList(genre));
        long id = bookDao.save(expectedBook);

        bookDao.deleteById(id);
        Optional<Book> actualBook = bookDao.getById(id);

        assertFalse(actualBook.isPresent());
    }

    @Test
    @DisplayName("Should to delete book by author id")
    void deleteByAuthorId() {
        Author author = new Author("Author2");
        Genre genre = new Genre("horror");
        long authorId = authorDao.save(author);
        author.setId(authorId);
        genre.setId(1);

        Book expectedBook = new Book("Book delete #2",
            author, Collections.singletonList(genre));
        long bookId = bookDao.save(expectedBook);

        bookDao.deleteByAuthorId(authorId);
        Optional<Book> actualBook = bookDao.getById(bookId);

        assertFalse(actualBook.isPresent());
    }

    @Test
    @DisplayName("Should to delete book by genre id")
    void deleteByGenreId() {
        Author author = new Author("author #1");
        Genre genre = new Genre("Genre3");
        long genreId = genreDao.save(genre);
        genre.setId(genreId);
        author.setId(1);

        Book expectedBook = new Book("Book delete #3",
            author, Collections.singletonList(genre));
        long bookId = bookDao.save(expectedBook);

        bookDao.deleteByGenreId(genreId);
        Optional<Book> actualBook = bookDao.getById(bookId);

        assertFalse(actualBook.isPresent());
    }
}
