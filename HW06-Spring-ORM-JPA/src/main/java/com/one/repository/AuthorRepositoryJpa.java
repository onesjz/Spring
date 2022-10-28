package com.one.repository;

import com.one.model.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    private static final Logger log = LoggerFactory.getLogger(AuthorRepositoryJpa.class);

    @PersistenceContext
    private final EntityManager manager;

    public AuthorRepositoryJpa(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == 0) {
            manager.persist(author);
            return author;
        }
        return update(author);
    }

    @Override
    public Author update(Author author) {
        return manager.merge(author);
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(manager.find(Author.class, id));
    }

    @Override
    public Optional<Author> findByName(String name) {
        try {
            TypedQuery<Author> queryAuthor = manager.createQuery("select a from Author a where a.name = :name", Author.class);
            queryAuthor.setParameter("name", name);
            return Optional.ofNullable(queryAuthor.getSingleResult());
        } catch (NoResultException e) {
            log.warn("Author is not found.");
            return Optional.empty();
        }
    }

    @Override
    public List<Author> findAll() {
        try {
            TypedQuery<Author> queryAuthors = manager.createQuery("select a from Author a", Author.class);
            return queryAuthors.getResultList();
        } catch (NoResultException e) {
            log.warn("The library has not authors");
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresent(manager::remove);
    }
}
