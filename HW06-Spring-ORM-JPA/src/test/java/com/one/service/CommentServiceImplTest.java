package com.one.service;

import com.one.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Comment service should")
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    private CommentService commentService;

    @BeforeEach
    void init() {
        commentService = new CommentServiceImpl(commentRepository);
    }

    @Test
    @DisplayName("Should to change the text of comment")
    void changeCommentText() {
        commentService.changeCommentText(1L, "text");

        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should to remove the comment")
    void removeComment() {
        commentService.removeComment(1L);

        verify(commentRepository, times(1)).deleteById(1L);
    }
}
