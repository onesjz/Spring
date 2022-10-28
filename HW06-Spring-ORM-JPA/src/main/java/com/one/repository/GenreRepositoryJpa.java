package com.one.repository;

import com.one.model.Genre;
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
public class GenreRepositoryJpa implements GenreRepository {

    private static final Logger log = LoggerFactory.getLogger(GenreRepositoryJpa.class);

    @PersistenceContext
    private final EntityManager manager;

    public GenreRepositoryJpa(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            manager.persist(genre);
            return genre;
        }
        return update(genre);
    }

    @Override
    public Genre update(Genre genre) {
        return manager.merge(genre);
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(manager.find(Genre.class, id));
    }

    @Override
    public List<Genre> findAll() {
        try {
            TypedQuery<Genre> queryGenres = manager.createQuery("select g from Genre g", Genre.class);
            return queryGenres.getResultList();
        } catch (NoResultException e) {
            log.warn("The library has not genres");
            return new ArrayList<>();
        }
    }

    @Override
    public List<Genre> findAllByNames(List<String> names) {
        try {
            TypedQuery<Genre> query = manager.createQuery("select g from Genre g where g.name in :names", Genre.class);
            query.setParameter("names", names);
            return query.getResultList();
        } catch (NoResultException e) {
            log.warn("The library has not genres {}", names);
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresent(manager::remove);
    }
}
