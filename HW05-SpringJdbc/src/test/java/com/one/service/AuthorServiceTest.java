package com.one.service;

import com.one.dao.AuthorDao;
import com.one.dao.BookDao;
import com.one.domain.Author;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Author service should")
class AuthorServiceTest {

    @Mock
    private AuthorDao authorDao;
    @Mock
    private BookDao bookDao;

    private AuthorService authorService;

    @BeforeEach
    void init() {
        authorService = new AuthorServiceImpl(authorDao, bookDao);
    }

    @Test
    @DisplayName("Should to create a new author")
    void createAuthor() {
        doReturn(1L).when(authorDao).save(any(Author.class));

        assertEquals(1, authorService.createAuthor("name"));
        verify(authorDao, times(1)).save(any(Author.class));
    }

    @Test
    @DisplayName("Should check author into db or create a new author")
    void checkAuthorOrCreate() {
        doReturn(1L).when(authorDao).save(any(Author.class));
        doReturn(Optional.empty()).when(authorDao).getByName("");
        doReturn(Optional.of(new Author("name"))).when(authorDao).getByName("name");

        authorService.checkAuthorOrCreate("");
        authorService.checkAuthorOrCreate("name");
        verify(authorDao, times(1)).save(any(Author.class));
        verify(authorDao, times(2)).getByName(anyString());
    }

    @Test
    @DisplayName("Should to get the author by id")
    void getAuthor() {
        doReturn(Optional.of(new Author("name"))).when(authorDao).getById(anyLong());
        Author actualAuthor = authorService.getAuthor(1);

        assertEquals("name", actualAuthor.getName());
    }

    @Test
    @DisplayName("Should to return all authors")
    void getAllAuthors() {
        doReturn(Collections.singletonList(new Author("name"))).when(authorDao).getAll();
        List<Author> actualAuthorList = authorService.getAllAuthors();

        assertEquals(1, actualAuthorList.size());
    }

    @Test
    @DisplayName("Should to delete author by id")
    void deleteAuthorById() {
        authorService.deleteAuthor(1);

        verify(bookDao, times(1)).deleteByAuthorId(1);
        verify(authorDao, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Should to delete author by name")
    void deleteAuthorByName() {
        doReturn(Optional.of(new Author("name"))).when(authorDao).getByName("name");
        authorService.deleteAuthor("name");

        verify(authorDao, times(1)).deleteById(anyLong());
    }
}
