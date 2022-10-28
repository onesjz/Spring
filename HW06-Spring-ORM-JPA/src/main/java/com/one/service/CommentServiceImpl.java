package com.one.service;

import com.one.model.Comment;
import com.one.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    @Override
    public void changeCommentText(long idComment, String nextText) {
        Optional<Comment> comment = commentRepository.findById(idComment);
        comment.ifPresent(c -> c.setText(nextText));
    }

    @Transactional
    @Override
    public void removeComment(long idComment) {
        commentRepository.deleteById(idComment);
    }
}
