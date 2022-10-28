package com.one.shell;

import com.one.service.CommentService;
import com.one.service.LibraryService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class CommandLinerLibrary {

    private final LibraryService libraryService;
    private final CommentService commentService;

    public CommandLinerLibrary(LibraryService libraryService, CommentService commentService) {
        this.libraryService = libraryService;
        this.commentService = commentService;
    }

    @ShellMethod(key = "add", value = "Add a new book to library")
    public void addBook(@ShellOption(value = "--name") String nameBook,
                        @ShellOption(value = "--author") String nameAuthor,
                        @ShellOption(value = "--genres") String[] genres) {
        libraryService.createBook(nameBook, nameAuthor, genres);
    }

    @ShellMethod(key = "change", value = "To change something for the book")
    public void changeBook(@ShellOption(value = "--id") long id,
                           @ShellOption(value = "--name", defaultValue = "") String nameBook,
                           @ShellOption(value = "--author", defaultValue = "") String nameAuthor) {
        libraryService.changeBook(id, nameBook, nameAuthor);
    }

    @ShellMethod(key = "delete", value = "Delete book from library")
    public void deleteBook(@ShellOption(value = "--id") long id) {
        libraryService.removeBook(id);
    }

    @ShellMethod(key = "comment", value = "Add comment for the book")
    public void addComment(@ShellOption(value = "--id") long idBook,
                           @ShellOption(value = "--name") String author,
                           @ShellOption(value = "--text") String[] textArray) {
        String nextText = String.join(" ", textArray);
        libraryService.addComment(idBook, author, nextText);
    }

    @ShellMethod(key = "changeC", value = "Change text of comment")
    public void changeComment(@ShellOption(value = "--id") long idComment,
                              @ShellOption(value = "--text") String[] textArray) {
        String nextText = String.join(" ", textArray);
        commentService.changeCommentText(idComment, nextText);
    }

    @ShellMethod(key = "deleteC", value = "Delete comment")
    public void deleteComment(@ShellOption(value = "--id") long id) {
        commentService.removeComment(id);
    }

    @ShellMethod(key = "info", value = "Show information about book or all books")
    public String showBook(@ShellOption(value = "--id", defaultValue = "0") long id) {
        if (id == 0) {
            return libraryService.getInfoAllBooks();
        } else {
            return libraryService.getInfoBook(id);
        }
    }

    @ShellMethod(value = "Close the library")
    public void exit() {
        System.exit(-1);
    }
}
