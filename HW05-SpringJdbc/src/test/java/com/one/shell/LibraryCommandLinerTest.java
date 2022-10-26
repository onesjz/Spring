package com.one.shell;

import com.one.domain.Author;
import com.one.domain.Book;
import com.one.domain.Genre;
import com.one.service.AuthorService;
import com.one.service.BookService;
import com.one.service.GenreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Library Command Liner Should")
class LibraryCommandLinerTest {

    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private BookService bookService;

    @Autowired
    private LibraryCommandLiner libraryCommandLiner;

    @Test
    @DisplayName("Should to create a new author")
    void createAuthor() {
        doReturn(1L).when(authorService).createAuthor("name");

        assertEquals(1, libraryCommandLiner.createAuthor("name"));
    }

    @Test
    @DisplayName("Should to show an information about author")
    void showAuthorInformation() {
        Book book = new Book();
        book.setId(1);
        doReturn(new Author("name")).when(authorService).getAuthor(1);
        doReturn(Collections.singletonList(book)).when(bookService).getBooksWithAuthorId(1);
        doReturn(Collections.singletonList(new Genre("genre"))).when(genreService).getAllGenreForBook(1);

        String expected = libraryCommandLiner.showAuthorInformation(1L);
        libraryCommandLiner.showAuthorInformation(0L);

        assertNotNull(expected);
        verify(authorService, times(1)).getAllAuthors();
        verify(authorService, times(1)).getAuthor(1L);
        verify(bookService, times(1)).getBooksWithAuthorId(1L);
        verify(genreService, times(1)).getAllGenreForBook(anyLong());
    }

    @Test
    @DisplayName("Should to delete author")
    void deleteAuthor() {
        libraryCommandLiner.deleteAuthor(0L, "test");
        libraryCommandLiner.deleteAuthor(1L, "");

        verify(authorService, times(1)).deleteAuthor("test");
        verify(authorService, times(1)).deleteAuthor(1);
    }

    @Test
    @DisplayName("Should to add a new book")
    void addBook() {
        doReturn(1L).when(bookService).createBook(anyString(), anyString(), anyList());

        assertEquals(1, libraryCommandLiner.addBook("1", "2", new String[]{"1"}));
    }

    @Test
    @DisplayName("Should to show an information about book")
    void showBookInformation() {
        Book book = new Book();
        book.setId(1);
        doReturn(book).when(bookService).getBook(1);
        doReturn(Collections.singletonList(book)).when(bookService).getBooks();
        doReturn(Collections.singletonList(new Genre("genre"))).when(genreService).getAllGenreForBook(1);

        String expected = libraryCommandLiner.showBookInformation(1L);
        libraryCommandLiner.showBookInformation(0L);

        assertNotNull(expected);
        verify(bookService, times(1)).getBooks();
        verify(bookService, times(1)).getBook(1L);
        verify(genreService, times(2)).getAllGenreForBook(anyLong());
    }

    @Test
    @DisplayName("Should to delete the book")
    void deleteBook() {
        libraryCommandLiner.deleteBook(0L, "name", 0L);
        libraryCommandLiner.deleteBook(1L, "", 0L);
        libraryCommandLiner.deleteBook(0L, "", 1L);

        verify(bookService, times(1)).deleteBook(1, true);
        verify(bookService, times(1)).deleteBook(1, false);
        verify(bookService, times(1)).deleteBook("name");
    }

    @Test
    @DisplayName("Should to add a new genre")
    void addGenre() {
        doReturn(1L).when(genreService).createGenre(anyString());

        assertEquals(1, libraryCommandLiner.addGenre("1"));
    }

    @Test
    @DisplayName("Should to show an information about genre")
    void showGenreInformation() {
        doReturn(new Genre()).when(genreService).getGenre(anyLong());
        libraryCommandLiner.showGenreInformation(1L);
        libraryCommandLiner.showGenreInformation(0L);

        verify(genreService, times(1)).getAllGenres();
        verify(genreService, times(1)).getGenre(1L);
        verify(bookService, times(1)).getBooksWithGenreId(1L);
    }

    @Test
    @DisplayName("Should to delete the genre")
    void deleteGenre() {
        libraryCommandLiner.deleteGenre(0L, "test");
        libraryCommandLiner.deleteGenre(1L, "");

        verify(genreService, times(1)).deleteGenre("test");
        verify(genreService, times(1)).deleteGenre(1);
    }
}
