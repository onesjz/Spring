package com.one.shell;

import com.one.service.CommentService;
import com.one.service.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Command liner library should")
class CommandLinerLibraryTest {

    @Mock
    private LibraryService libraryService;
    @Mock
    private CommentService commentService;

    private CommandLinerLibrary commandLinerLibrary;

    @BeforeEach
    void init() {
        commandLinerLibrary = new CommandLinerLibrary(libraryService, commentService);
    }

    @Test
    @DisplayName("Should to call the createBook method by shell command")
    void addBook() {
        commandLinerLibrary.addBook("book", "author", new String[]{"genre1", "genre2"});

        verify(libraryService, times(1))
            .createBook("book", "author", "genre1", "genre2");
    }

    @Test
    @DisplayName("Should to call the changeBook method by shell command")
    void changeBook() {
        commandLinerLibrary.changeBook(1L, "book", "author");

        verify(libraryService, times(1))
            .changeBook(1L, "book", "author");
    }

    @Test
    @DisplayName("Should to call the deleteBook method by shell command")
    void deleteBook() {
        commandLinerLibrary.deleteBook(1L);

        verify(libraryService, times(1)).removeBook(1L);
    }

    @Test
    @DisplayName("Should to call the addComment method by shell command")
    void addComment() {
        commandLinerLibrary.addComment(1L, "author", new String[]{"text"});

        verify(libraryService, times(1))
            .addComment(1L, "author", "text");
    }

    @Test
    @DisplayName("Should to call the changeComment method by shell command")
    void changeComment() {
        commandLinerLibrary.changeComment(1L, new String[]{"text"});

        verify(commentService, times(1))
            .changeCommentText(1L, "text");
    }

    @Test
    @DisplayName("Should to call the deleteComment method by shell command")
    void deleteComment() {
        commandLinerLibrary.deleteComment(1L);

        verify(commentService, times(1)).removeComment(1L);
    }

    @Test
    @DisplayName("Should to call the showBook method by shell command")
    void showBook() {
        commandLinerLibrary.showBook(1L);

        verify(libraryService, times(1)).getInfoBook(1L);
        verify(libraryService, never()).getInfoAllBooks();
    }

    @Test
    @DisplayName("Should to call the showAllBook method by shell command")
    void showAllBooks() {
        commandLinerLibrary.showBook(0);

        verify(libraryService, times(1)).getInfoAllBooks();
        verify(libraryService, never()).getInfoBook(anyLong());
    }
}
