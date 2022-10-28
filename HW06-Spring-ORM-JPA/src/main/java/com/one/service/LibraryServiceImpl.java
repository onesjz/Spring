package com.one.service;

import com.one.model.Author;
import com.one.model.Book;
import com.one.model.Comment;
import com.one.model.Genre;
import com.one.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    private static final Logger log = LoggerFactory.getLogger(LibraryServiceImpl.class);

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    public LibraryServiceImpl(AuthorService authorService, BookRepository bookRepository,
                              GenreService genreService) {
        this.authorService = authorService;
        this.bookRepository = bookRepository;
        this.genreService = genreService;
    }

    @Transactional
    @Override
    public void createBook(String nameBook, String nameAuthor, String... nameGenres) {
        Author author = authorService.checkOrCreateAuthor(nameAuthor);
        List<Genre> genres = genreService.checkOrUpdateGenre(Arrays.asList(nameGenres));

        Book book = new Book(nameBook, author, genres);
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void changeBook(long idBook, String nameBook, String nameAuthor) {
        Optional<Book> bookOptional = bookRepository.findById(idBook);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            if (!nameBook.isEmpty()) {
                book.setName(nameBook);
            }

            Author author = authorService.getAuthor(book.getAuthor().getId());
            if (!nameAuthor.isEmpty()) {
                author.setName(nameAuthor);
            }
            book.setAuthor(author);

            bookRepository.update(book);
        }
    }

    @Transactional
    @Override
    public void removeBook(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void addComment(long idBook, String authorComment, String text) {
        Optional<Book> bookOptional = bookRepository.findById(idBook);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.addComment(new Comment(authorComment, text, book));
            bookRepository.update(book);
        }
    }

    @Transactional
    @Override
    public String getInfoBook(long idBook) {
        Optional<Book> book = bookRepository.findById(idBook);

        if (book.isPresent()) {
            return book.toString();
        } else {
            log.warn("Book with id {} is not found.", idBook);
            return "";
        }
    }

    @Transactional
    @Override
    public String getInfoAllBooks() {
        List<Book> books = bookRepository.findAll();

        if (books == null || books.isEmpty()) {
            log.warn("Library is empty.");
            return "";
        } else {
            return String.join(",", books.stream().map(Object::toString).toList());
        }
    }
}
