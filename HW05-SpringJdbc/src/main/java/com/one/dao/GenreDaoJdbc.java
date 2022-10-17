package com.one.dao;

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
public class GenreDaoJdbc implements GenreDao {

    private static final Logger log = LoggerFactory.getLogger(GenreDaoJdbc.class);

    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<Genre> getById(long id) {
        Genre genre = null;
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            genre = jdbc.queryForObject("select id, genre_name from genres where id = :id", params, rowMapper);
        } catch (Exception e) {
            log.warn("Genre with id {} is not found", id);
        }
        return Optional.ofNullable(genre);
    }

    @Override
    public Optional<Genre> getByName(String name) {
        Genre genre = null;
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            genre = jdbc.queryForObject("select id, genre_name from genres where genre_name = :name", params, rowMapper);
        } catch (Exception e) {
            log.warn("Genre {} is not found", name);
        }
        return Optional.ofNullable(genre);
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id, genre_name from genres", rowMapper);
    }

    @Override
    public List<Genre> getAllByNames(List<String> names) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("names", names);
        return jdbc.query("select id, genre_name from genres where genre_name in (:names)", parameterSource, rowMapper);
    }

    @Override
    public List<Genre> getAllByBookId(long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        return jdbc.query("select g.id, g.genre_name from genres g left join book_genre bg on g.id = bg.genre_id where bg.book_id = :id", parameterSource, rowMapper);
    }

    @Override
    public long save(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameterSource = new MapSqlParameterSource("name", genre.getName());

        jdbc.update("insert into genres (genre_name) values (:name)",
            parameterSource, keyHolder);

        return (long) keyHolder.getKeys().get("id");
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Long> ids = jdbc.query("select bg.book_id from book_genre bg left join genres g on bg.genre_id = g.id where g.id = :id", params, ((rs, rowNum) -> rs.getLong("book_id")));
        jdbc.update("delete from book_genre where genre_id = :id", params);
        for (Long bookId : ids) {
            jdbc.update("delete from books where id = :id", Map.of("id", bookId));
        }
        jdbc.update("delete from genres where id = :id", params);
    }

    public final RowMapper<Genre> rowMapper = ((rs, rowNum) -> {
        long id = rs.getLong("id");
        String name = rs.getString("genre_name");

        return new Genre(id, name);
    });
}
