package com.one.service;

import com.one.domain.Book;

import java.util.List;

public interface BookService {
    long createBook(String authorName, String bookName, List<String> genreNames);

    Book getBook(long id);

    List<Book> getBooks();

    List<Book> getBooksWithGenreId(long genreId);

    List<Book> getBooksWithAuthorId(long authorId);

    void deleteBook(String name);

    void deleteBook(long id, boolean isAuthorId);
}
