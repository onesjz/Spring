package com.one.service;

import com.one.model.Genre;
import com.one.repository.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional
    @Override
    public List<Genre> checkOrUpdateGenre(List<String> names) {
        List<Genre> actualGenres = getAllGenreByNames(names);

        if (actualGenres.size() == names.size()) {
            return actualGenres;
        }
        List<String> actualGenreNames = actualGenres.stream().map(Genre::getName).toList();
        actualGenres.addAll(names.stream()
            .filter(n -> !actualGenreNames.contains(n))
            .map(Genre::new).toList());

        return actualGenres.stream().map(genreRepository::save).toList();
    }

    @Override
    public List<Genre> getAllGenreByNames(List<String> nameGenres) {
        return genreRepository.findAllByNames(nameGenres);
    }
}
