package com.one.dao;

import com.one.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Optional<Book> getById(long id);

    Optional<Book> getByName(String name);

    List<Book> getAll();

    List<Book> getAllByGenreId(long id);

    List<Book> getByAuthorId(long id);

    long save(Book book);

    void deleteById(long id);

    void deleteByAuthorId(long id);

    void deleteByGenreId(long id);
}
