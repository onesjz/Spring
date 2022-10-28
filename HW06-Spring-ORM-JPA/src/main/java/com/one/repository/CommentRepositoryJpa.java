package com.one.repository;

import com.one.model.Comment;
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
public class CommentRepositoryJpa implements CommentRepository {

    private static final Logger log = LoggerFactory.getLogger(CommentRepositoryJpa.class);

    @PersistenceContext
    private final EntityManager manager;

    public CommentRepositoryJpa(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            manager.persist(comment);
            return comment;
        }
        return update(comment);
    }

    @Override
    public Comment update(Comment comment) {
        return manager.merge(comment);
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(manager.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAllByBookId(long id) {
        try {
            TypedQuery<Comment> queryComments = manager.createQuery("select c from Comment c where c.book.id = :id", Comment.class);
            queryComments.setParameter("id", id);
            return queryComments.getResultList();
        } catch (NoResultException e) {
            log.warn("The book {} has not comments", id);
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresent(manager::remove);
    }
}
