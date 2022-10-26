package com.one.service;

import com.one.dao.BookDao;
import com.one.domain.Author;
import com.one.domain.Book;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Book service should")
class BookServiceTest {

    @Mock
    private BookDao bookDao;
    @Mock
    private AuthorService authorService;
    @Mock
    private GenreService genreService;

    private BookService bookService;

    @BeforeEach
    void init() {
        bookService = new BookServiceImpl(bookDao, authorService, genreService);
    }

    @Test
    @DisplayName("Should to create a new book")
    void createBook() {
        doReturn(1L).when(bookDao).save(any(Book.class));

        assertEquals(1, bookService.createBook("", "", Collections.emptyList()));
    }

    @Test
    @DisplayName("Should to get the book")
    void getBook() {
        doReturn(Optional.of(new Book("test", new Author(""),
            Collections.emptyList()))).when(bookDao).getById(1);
        Book book = bookService.getBook(1);

        assertEquals("test", book.getName());
    }

    @Test
    @DisplayName("Should to get all books")
    void getBooks() {
        doReturn(Collections.singletonList(new Book())).when(bookDao).getAll();
        List<Book> books = bookService.getBooks();

        assertFalse(books.isEmpty());
    }

    @Test
    @DisplayName("Should to get all books with genre id")
    void getBooksWithGenreId() {
        doReturn(Collections.singletonList(new Book())).when(bookDao).getAllByGenreId(1);
        List<Book> books = bookService.getBooksWithGenreId(1);

        assertFalse(books.isEmpty());
    }

    @Test
    @DisplayName("Should to get all books with author id")
    void getBooksWithAuthorId() {
        doReturn(Collections.singletonList(new Book())).when(bookDao).getByAuthorId(1);
        List<Book> books = bookService.getBooksWithAuthorId(1);

        assertFalse(books.isEmpty());
    }

    @Test
    @DisplayName("Should to delete book by id")
    void deleteBookById() {
        bookService.deleteBook(1, true);
        bookService.deleteBook(2, false);

        verify(bookDao, times(1)).deleteByAuthorId(1);
        verify(bookDao, times(1)).deleteById(2);
    }

    @Test
    @DisplayName("Should to delete book by name")
    void deleteBookByName() {
        doReturn(Optional.of(new Book())).when(bookDao).getByName("");
        bookService.deleteBook("");

        verify(bookDao, times(1)).getByName(anyString());
        verify(bookDao, times(1)).deleteById(anyLong());
    }
}
