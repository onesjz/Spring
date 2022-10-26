package com.one.dao;

import com.one.domain.Author;
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
public class AuthorDaoJdbc implements AuthorDao {

    private static final Logger log = LoggerFactory.getLogger(AuthorDaoJdbc.class);

    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<Author> getById(long id) {
        Author author = null;
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            author = jdbc.queryForObject("select id, author_name from authors where id = :id", params, rowMapper);
        } catch (Exception e) {
            log.warn("Author with id {} is not found", id);
        }
        return Optional.ofNullable(author);
    }

    @Override
    public Optional<Author> getByName(String name) {
        Author author = null;
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            author = jdbc.queryForObject("select id, author_name from authors where author_name = :name", params, rowMapper);
        } catch (Exception e) {
            log.warn("Author with name {} is not found", name);
        }
        return Optional.ofNullable(author);
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, author_name from authors", rowMapper);
    }

    @Override
    public long save(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource("name", author.getName());
        jdbc.update("insert into authors (author_name) values (:name)",
            parameterSource, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from authors where id = :id", params);
    }

    private final RowMapper<Author> rowMapper = ((rs, rowNum) -> {
        long id = rs.getLong("id");
        String name = rs.getString("author_name");

        return new Author(id, name);
    });
}
