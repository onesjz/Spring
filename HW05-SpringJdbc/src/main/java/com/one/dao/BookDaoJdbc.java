package com.one.dao;

import com.one.domain.Author;
import com.one.domain.Book;
import com.one.domain.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookDaoJdbc implements BookDao {

    private static final Logger log = LoggerFactory.getLogger(BookDaoJdbc.class);

    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<Book> getById(long id) {
        Book book = null;
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            book = jdbc.queryForObject("select b.id, b.book_name, a.author_name from books b left join authors a on a.id = b.author_id where b.id = :id", params, rowMapper);
        } catch (Exception e) {
            log.warn("Book with id {} is not found", id);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public Optional<Book> getByName(String name) {
        Book book = null;
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            book = jdbc.queryForObject("select b.id, b.book_name, a.author_name from books b left join authors a on a.id = b.author_id where b.book_name = :name", params, rowMapper);
        } catch (Exception e) {
            log.warn("Book {} is not found", name);
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select b.id, b.book_name, a.author_name from books b left join authors a on a.id = b.author_id", rowMapper);
    }

    @Override
    public List<Book> getAllByGenreId(long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return jdbc.query("select b.id, b.book_name, a.author_name from books b left join authors a on a.id = b.author_id left join book_genre bg on b.id = bg.book_id where bg.genre_id = :id", parameterSource, rowMapper);
    }

    @Override
    public List<Book> getByAuthorId(long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return jdbc.query("select b.id, b.book_name, a.author_name from books b left join authors a on a.id = b.author_id where a.id = :id", parameterSource, rowMapper);
    }

    @Override
    public long save(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("name", book.getName())
            .addValue("authorId", book.getAuthor().getId());
        jdbc.update("insert into books (book_name, author_id) values (:name, :authorId)",
            parameterSource, keyHolder);
        long id = (long) keyHolder.getKeys().get("id");
        book.setId(id);
        updateSyncTable(book);

        return (long) keyHolder.getKeys().get("id");
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        deleteBookFromSyncTable(params);
        jdbc.update("delete from books where id = :id", params);
    }

    @Override
    public void deleteByAuthorId(long id) {
        List<Long> ids = jdbc.query("select id from books where author_id = :id",
            Map.of("id", id), ((rs, rowNum) -> rs.getLong("id")));

        for (Long idBook : ids) {
            deleteBookFromSyncTable(Map.of("id", idBook));
        }
        jdbc.update("delete from books where author_id = :id", Map.of("id", id));
    }

    @Override
    public void deleteByGenreId(long id) {
        List<Long> bookIds = jdbc.query("select b.id from books b left join book_genre bg on b.id = bg.book_id where bg.genre_id = :genreId",
            Map.of("genreId", id), (rs, rowNum) -> rs.getLong("id"));

        for (Long bookId : bookIds) {
            deleteById(bookId);
        }
    }

    private void updateSyncTable(Book book) {
        for (Genre genre : book.getGenres()) {
            jdbc.update("insert into book_genre (book_id, genre_id) values (:bookId, :genreId)",
                Map.of("bookId", book.getId(), "genreId", genre.getId()));
        }
    }

    private void deleteBookFromSyncTable(Map<String, Object> params) {
        jdbc.update("delete from book_genre where book_id = :id", params);
    }

    private final RowMapper<Book> rowMapper = ((rs, rowNum) -> {
        long id = rs.getLong("id");
        String name = rs.getString("book_name");
        String authorName = rs.getString("author_name");

        return new Book(id, name, new Author(authorName));
    });
}
