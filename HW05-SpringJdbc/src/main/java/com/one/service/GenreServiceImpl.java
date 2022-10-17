package com.one.service;

import com.one.dao.GenreDao;
import com.one.domain.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private static final Logger log = LoggerFactory.getLogger(GenreServiceImpl.class);

    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public long createGenre(String name) {
        long id = genreDao.save(new Genre(name));
        log.trace("Genre was created and get id {}", id);
        return id;
    }

    @Override
    public List<Genre> updateGenre(List<String> names) {
        List<String> newGenres;
        List<Genre> genres = genreDao.getAllByNames(names);

        if (!genres.isEmpty()) {
            newGenres = names.stream()
                .filter(name -> !genres.stream()
                    .map(Genre::getName).toList().contains(name)).toList();
        } else {
            newGenres = names;
        }

        if (newGenres.isEmpty()) {
            return genres;
        }

        for (String name : newGenres) {
            genreDao.save(new Genre(name));
        }

        return genreDao.getAllByNames(names);
    }

    @Override
    public Genre getGenre(long id) {
        Optional<Genre> genre = genreDao.getById(id);

        if (genre.isPresent()) {
            return genre.get();
        } else {
            log.warn("Genre is not found!");
        }
        return new Genre();
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genres = genreDao.getAll();

        if (genres.isEmpty()) {
            log.debug("Library has not genres!");
        }
        return genres;
    }

    @Override
    public List<Genre> getAllGenreForBook(long bookId) {
        List<Genre> genres = genreDao.getAllByBookId(bookId);

        if (genres.isEmpty()) {
            log.debug("Library has not genres!");
        }
        return genres;
    }

    @Override
    public void deleteGenre(long id) {
        genreDao.deleteById(id);
        log.trace("Genre with id {} was deleted!", id);
    }

    @Override
    public void deleteGenre(String name) {
        Optional<Genre> genre = genreDao.getByName(name);
        genre.ifPresent(value -> genreDao.deleteById(value.getId()));
        log.trace("Genre {} was deleted!", name);
    }
}
