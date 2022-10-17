package com.one.service;

import com.one.dao.BookDao;
import com.one.domain.Author;
import com.one.domain.Book;
import com.one.domain.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImpl(BookDao bookDao, AuthorService authorService, GenreService genreService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public long createBook(String authorName, String bookName, List<String> genreNames) {
        Author author = authorService.checkAuthorOrCreate(authorName);
        List<Genre> genres = genreService.updateGenre(genreNames);

        long id = bookDao.save(new Book(bookName, author, genres));
        log.trace("Author was created and get id {}", id);
        return id;
    }

    @Override
    public Book getBook(long bookId) {
        Optional<Book> book = bookDao.getById(bookId);

        if (book.isPresent()) {
            return book.get();
        } else {
            log.warn("Book is not found!");
        }
        return new Book();
    }

    @Override
    public List<Book> getBooks() {
        List<Book> books = bookDao.getAll();

        if (!books.isEmpty()) {
            log.debug("Library has not authors!");
        }
        return books;
    }

    @Override
    public List<Book> getBooksWithGenreId(long genreId) {
        List<Book> books = bookDao.getAllByGenreId(genreId);

        if (!books.isEmpty()) {
            log.debug("Library has not authors!");
        }
        return books;
    }

    @Override
    public List<Book> getBooksWithAuthorId(long authorId) {
        List<Book> books = bookDao.getByAuthorId(authorId);

        if (!books.isEmpty()) {
            log.debug("Library has not authors!");
        }
        return books;
    }

    @Override
    public void deleteBook(String name) {
        Optional<Book> book = bookDao.getByName(name);
        book.ifPresent(b -> bookDao.deleteById(b.getId()));
        log.trace("Book {} was deleted!", name);
    }

    @Override
    public void deleteBook(long id, boolean isAuthorId) {
        if (isAuthorId) {
            bookDao.deleteByAuthorId(id);
        } else {
            bookDao.deleteById(id);
        }
        log.trace("Book with id {} was deleted!", id);
    }
}
