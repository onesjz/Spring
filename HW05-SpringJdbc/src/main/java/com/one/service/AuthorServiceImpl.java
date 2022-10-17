package com.one.service;

import com.one.dao.AuthorDao;
import com.one.dao.BookDao;
import com.one.domain.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

    private final AuthorDao authorDao;
    private final BookDao bookDao;

    public AuthorServiceImpl(AuthorDao authorDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
    }


    @Override
    public long createAuthor(String name) {
        long id = authorDao.save(new Author(name));
        log.trace("Author was created and get id {}", id);
        return id;
    }

    @Override
    public Author checkAuthorOrCreate(String name) {
        Optional<Author> optionalAuthor = authorDao.getByName(name);

        if(optionalAuthor.isPresent()) {
            return optionalAuthor.get();
        } else {
            Author author = new Author(name);
            author.setId(authorDao.save(author));
            return author;
        }
    }

    @Override
    public Author getAuthor(long id) {
        Optional<Author> author = authorDao.getById(id);

        if (author.isPresent()) {
            return author.get();
        } else {
            log.warn("Author is not found!");
        }
        return new Author();
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = authorDao.getAll();

        if (!authors.isEmpty()) {
            log.debug("Library has not authors!");
        }
        return authors;
    }

    @Override
    public void deleteAuthor(long id) {
        bookDao.deleteByAuthorId(id);
        authorDao.deleteById(id);
        log.trace("Author with id {} was deleted!", id);
    }

    @Override
    public void deleteAuthor(String name) {
        Optional<Author> author = authorDao.getByName(name);
        author.ifPresent(a -> authorDao.deleteById(a.getId()));
        log.trace("Author {} was deleted!", name);
    }
}
