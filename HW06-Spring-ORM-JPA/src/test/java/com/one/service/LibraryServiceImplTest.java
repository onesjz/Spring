package com.one.service;

import com.one.model.Author;
import com.one.model.Book;
import com.one.model.Genre;
import com.one.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Library service should")
class LibraryServiceImplTest {

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;

    @Autowired
    private LibraryService libraryService;

    @Test
    @DisplayName("Should to create a book and create or update author and genres")
    void createBook() {
        libraryService.createBook("name", "", "");

        verify(authorService, times(1)).checkOrCreateAuthor(anyString());
        verify(genreService, times(1)).checkOrUpdateGenre(anyList());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Should to change something of book")
    void changeBook() {
        Book book = new Book("name", new Author("author"), List.of(new Genre("genre")));
        doReturn(Optional.of(book)).when(bookRepository).findById(1L);
        doReturn(new Author()).when(authorService).getAuthor(anyLong());
        libraryService.changeBook(1L, "test", "test");

        verify(bookRepository, times(1)).update(any(Book.class));
    }

    @Test
    @DisplayName("Should to remove the book")
    void removeBook() {
        libraryService.removeBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should to add a new comment for the book")
    void addComment() {
        doReturn(Optional.of(new Book())).when(bookRepository).findById(1L);
        doReturn(Optional.empty()).when(bookRepository).findById(2L);
        libraryService.addComment(1L, "", "");
        libraryService.addComment(2L, "", "");

        verify(bookRepository, times(1)).update(any(Book.class));
    }

    @Test
    @DisplayName("Should to get string with all information about book")
    void getInfoBook() {
        doReturn(Optional.of(new Book("123", new Author(), List.of(new Genre("genre")))))
            .when(bookRepository).findById(anyLong());
        String actual = libraryService.getInfoBook(1L);

        assertFalse(actual.isEmpty());
    }

    @Test
    @DisplayName("Should to get string with all information all books")
    void getInfoAllBooks() {
        doReturn(List.of(new Book("123", new Author(), List.of(new Genre("genre")))))
            .when(bookRepository).findAll();
        String actual = libraryService.getInfoAllBooks();

        assertFalse(actual.isEmpty());
    }
}
