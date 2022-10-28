package com.one.service;

public interface LibraryService {

    void createBook(String nameBook, String nameAuthor, String... nameGenres);

    void changeBook(long idBook, String nameBook, String nameAuthor);

    void removeBook(long id);

    void addComment(long idBook, String authorComment, String text);

    String getInfoBook(long idBook);

    String getInfoAllBooks();
}
