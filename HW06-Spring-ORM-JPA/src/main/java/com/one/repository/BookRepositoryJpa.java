package com.one.repository;

import com.one.model.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager manager;

    public BookRepositoryJpa(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            manager.persist(book);
            return book;
        }
        return update(book);
    }

    @Override
    public Book update(Book book) {
        return manager.merge(book);
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(manager.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = manager.getEntityGraph("book-entity-graph");
        TypedQuery<Book> queryBooks = manager.createQuery("select b from Book b", Book.class);
        queryBooks.setHint("javax.persistence.fetchgraph", entityGraph);
        return queryBooks.getResultList();
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresent(manager::remove);
    }
}
