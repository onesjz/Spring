package com.one.repository;

import com.one.model.Author;
import com.one.model.Book;
import com.one.model.Genre;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(BookRepositoryJpa.class)
@DisplayName("Book repository should")
class BookRepositoryJpaTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Should to save a book correclty")
    void save() {
        Author author = new Author("name");
        Genre genre = new Genre("genre");
        entityManager.persist(author);
        entityManager.persist(genre);
        Book actualBook = bookRepository.save(new Book("book", author, List.of(genre)));
        Book expectedBook = entityManager.find(Book.class, actualBook.getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("Should to update the book")
    void update() {
        Book book = entityManager.find(Book.class, 1L);
        book.setName("test");
        Book actualBook = bookRepository.update(book);

        assertThat(actualBook.getName()).isEqualTo("test");
    }

    @Test
    @DisplayName("Should to find the book by id")
    void findById() {
        Optional<Book> actualBook = bookRepository.findById(2L);

        assertThat(actualBook).isPresent()
            .get().extracting("id").isEqualTo(2L);
    }

    @Test
    @DisplayName("Should to find all books")
    void findAll() {
        SessionFactory sessionFactory = entityManager.getEntityManager().getEntityManagerFactory()
            .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Book> books = bookRepository.findAll();

        assertThat(books.size()).isGreaterThan(1);
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should to delete the book by id")
    void deleteById() {
        Author author = new Author("name");
        Genre genre = new Genre("genre");
        entityManager.persist(author);
        entityManager.persist(genre);
        Book book = bookRepository.save(new Book("qwe", author, List.of(genre)));
        Book actualBook = entityManager.find(Book.class, book.getId());
        bookRepository.deleteById(book.getId());
        Book expectedBook = entityManager.find(Book.class, book.getId());

        assertThat(expectedBook).isNotEqualTo(actualBook);
    }
}
