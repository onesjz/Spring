package com.one.repository;

import com.one.model.Book;
import com.one.model.Comment;
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
@Import(CommentRepositoryJpa.class)
@DisplayName("Comment repository should")
class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Should to save a comment correctly")
    void save() {
        Book book = entityManager.find(Book.class, 1L);
        Comment actualComment = commentRepository.save(new Comment("Nick", "123", book));
        Comment expectedComment = entityManager.find(Comment.class, actualComment.getId());

        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    @DisplayName("Should to update the comment")
    void update() {
        Book book = entityManager.find(Book.class, 1L);
        Comment comment = commentRepository.save(new Comment("Bob", "321", book));
        comment.setText("test");
        Comment actualComment = commentRepository.update(comment);

        assertThat(actualComment.getText()).isEqualTo("test");
    }

    @Test
    @DisplayName("Should to find the comment by id")
    void findById() {
        Book book = entityManager.find(Book.class, 1L);
        Comment comment = commentRepository.save(new Comment("John", "222", book));
        Optional<Comment> actualComment = commentRepository.findById(comment.getId());

        assertThat(actualComment).isPresent()
            .get().extracting("id").isEqualTo(comment.getId());
    }

    @Test
    @DisplayName("Should to find all comments by the book id")
    void findAllByBookId() {
        Book book = entityManager.find(Book.class, 1L);
        commentRepository.save(new Comment("Kate", "333", book));
        List<Comment> comments = commentRepository.findAllByBookId(book.getId());

        assertThat(comments.size()).isNotZero();
    }

    @Test
    @DisplayName("Should to delete the comment by id")
    void deleteById() {
        Book book = entityManager.find(Book.class, 1L);
        Comment comment = commentRepository.save(new Comment("July", "444", book));
        Comment actualComment = entityManager.find(Comment.class, comment.getId());
        commentRepository.deleteById(comment.getId());
        Comment expectedComment = entityManager.find(Comment.class, comment.getId());

        assertThat(expectedComment).isNotEqualTo(actualComment);
    }
}
