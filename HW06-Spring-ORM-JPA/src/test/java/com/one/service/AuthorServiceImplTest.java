package com.one.service;

import com.one.model.Author;
import com.one.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Author service should")
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    private AuthorService authorService;

    @BeforeEach
    void init() {
        authorService = new AuthorServiceImpl(authorRepository);
    }

    @Test
    @DisplayName("Should to create a new author")
    void createAuthor() {
        doReturn(new Author("author")).when(authorRepository).save(any(Author.class));
        Author actualAuthor = authorService.createAuthor("author");

        assertEquals("author", actualAuthor.getName());
    }

    @Test
    @DisplayName("Should to try get the author or create")
    void checkOrCreateAuthor() {
        doReturn(Optional.of(new Author(1, "author", new ArrayList<>())))
            .when(authorRepository).findByName("name");
        doReturn(Optional.of(new Author("author"))).when(authorRepository).findByName("");
        doReturn(new Author(2, "name", new ArrayList<>()))
            .when(authorRepository).save(any(Author.class));
        Author actualAuthorFirst = authorService.checkOrCreateAuthor("name");
        Author actualAuthorSecond = authorService.checkOrCreateAuthor("");

        assertEquals(1, actualAuthorFirst.getId());
        assertEquals(2, actualAuthorSecond.getId());
        verify(authorRepository, times(1)).save(any(Author.class));
        verify(authorRepository, times(2)).findByName(anyString());
    }

    @Test
    @DisplayName("Should to get the author by id")
    void getAuthorById() {
        doReturn(Optional.of(new Author("name"))).when(authorRepository).findById(1L);
        Author actualAuthor = authorService.getAuthor(1L);

        assertEquals("name", actualAuthor.getName());
    }

    @Test
    @DisplayName("Should to get the author by name")
    void GetAuthorByName() {
        doReturn(Optional.of(new Author("name"))).when(authorRepository).findByName("name");
        Author actualAuthor = authorService.getAuthor("name");

        assertEquals("name", actualAuthor.getName());
    }

    @Test
    @DisplayName("Should to remove the author by id")
    void removeAuthor() {
        authorService.removeAuthor(1L);

        verify(authorRepository, times(1)).deleteById(1L);
    }
}
