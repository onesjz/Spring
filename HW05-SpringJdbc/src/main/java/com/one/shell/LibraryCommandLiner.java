package com.one.shell;

import com.one.domain.Author;
import com.one.domain.Book;
import com.one.domain.Genre;
import com.one.service.AuthorService;
import com.one.service.BookService;
import com.one.service.GenreService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Arrays;
import java.util.List;

@ShellComponent
public class LibraryCommandLiner {

    private final AuthorService authorService;
    private final GenreService genreService;

    private final BookService bookService;

    public LibraryCommandLiner(AuthorService authorService, GenreService genreService, BookService bookService) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookService = bookService;
    }

    @ShellMethod(key = {"author -c", "author --create"}, value = "Create a new Author for library")
    public long createAuthor(@ShellOption(value = {"-n", "--name"}) String name) {
        return authorService.createAuthor(name);
    }

    @ShellMethod(key = "author", value = "Show author information")
    public String showAuthorInformation(@ShellOption(value = {"-i", "--id"}, defaultValue = StringUtils.EMPTY) Long id) {
        if (id == 0) {
            return authorService.getAllAuthors().toString();
        }

        Author author = authorService.getAuthor(id);
        List<Book> books = bookService.getBooksWithAuthorId(id);

        for (Book book : books) {
            List<Genre> genres = genreService.getAllGenreForBook(book.getId());
            book.setGenres(genres);
        }
        author.setBooks(books);

        return author.toString();
    }

    @ShellMethod(key = {"author -d", "author --delete"}, value = "Delete the current Author from library")
    public void deleteAuthor(@ShellOption(value = {"-i", "--id"}, defaultValue = "0") Long id,
                             @ShellOption(value = {"-n", "--name"}, defaultValue = StringUtils.EMPTY) String name) {
        if (id == 0) {
            authorService.deleteAuthor(name);
        } else {
            authorService.deleteAuthor(id);
        }
    }

    @ShellMethod(key = {"book -a", "book --add"}, value = "Create a new book for library")
    public long addBook(@ShellOption(value = {"-a", "--authorName"}) String authorName,
                        @ShellOption(value = {"-b", "--bookName"}) String bookName,
                        @ShellOption(value = {"-g", "--genreNames"}) String[] genres) {
        return bookService.createBook(authorName, bookName, Arrays.asList(genres));
    }

    @ShellMethod(key = {"book -i", "book --info"}, value = "Show book information")
    public String showBookInformation(@ShellOption(value = {"-i", "--id"}, defaultValue = "0") Long id) {
        if (id == 0) {
            List<Book> books = bookService.getBooks();
            for (Book book : books) {
                List<Genre> genres = genreService.getAllGenreForBook(book.getId());
                book.setGenres(genres);
            }
            return books.toString();
        }
        Book book = bookService.getBook(id);
        List<Genre> genres = genreService.getAllGenreForBook(id);
        book.setGenres(genres);

        return book.toString();
    }

    @ShellMethod(key = {"book -d", "book --delete"}, value = "Delete the book from library")
    public void deleteBook(@ShellOption(value = {"-i", "--id"}, defaultValue = "0") Long id,
                           @ShellOption(value = {"-n", "--name"}, defaultValue = StringUtils.EMPTY) String name,
                           @ShellOption(value = {"-a", "--author-id"}, defaultValue = "0") Long authorId) {
        if (id == 0 && authorId == 0) {
            bookService.deleteBook(name);
        } else if (id == 0) {
            bookService.deleteBook(authorId, true);
        } else {
            bookService.deleteBook(id, false);
        }
    }

    @ShellMethod(key = {"genre -a", "genre --add"}, value = "Add a new genre")
    public long addGenre(@ShellOption(value = {"-n", "--name"}) String name) {
        return genreService.createGenre(name);
    }

    @ShellMethod(key = {"genre -i", "genre --info"}, value = "Show genre information")
    public String showGenreInformation(@ShellOption(value = {"-i", "--id"}, defaultValue = "0") Long id) {
        if (id == 0) {
            return genreService.getAllGenres().toString();
        }
        Genre genre = genreService.getGenre(id);
        List<Book> books = bookService.getBooksWithGenreId(id);
        genre.setBooks(books);

        return genre.toString();
    }

    @ShellMethod(key = {"genre -d", "genre --delete"}, value = "Delete the genre from library")
    public void deleteGenre(@ShellOption(value = {"-i", "--id"}, defaultValue = "0") Long id,
                            @ShellOption(value = {"-n", "--name"}, defaultValue = StringUtils.EMPTY) String name) {
        if (id == 0) {
            genreService.deleteGenre(name);
        } else {
            genreService.deleteGenre(id);
        }
    }

    @ShellMethod(value = "Close the library")
    public void exit() {
        System.exit(-1);
    }
}
