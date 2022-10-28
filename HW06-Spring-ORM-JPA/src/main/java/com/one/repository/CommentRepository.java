package com.one.repository;

import com.one.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    Comment update(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findAllByBookId(long id);

    void deleteById(long id);
}
