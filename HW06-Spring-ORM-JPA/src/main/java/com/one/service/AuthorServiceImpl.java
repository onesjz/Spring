package com.one.service;

import com.one.model.Author;
import com.one.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    @Override
    public Author createAuthor(String name) {
        return authorRepository.save(new Author(name));
    }

    @Transactional
    @Override
    public Author checkOrCreateAuthor(String name) {
        Author author = getAuthor(name);
        if (author.getId() == 0) {
            author = createAuthor(name);
        }
        return author;
    }

    @Transactional
    @Override
    public Author getAuthor(long idAuthor) {
        Optional<Author> author = authorRepository.findById(idAuthor);
        return author.orElseGet(Author::new);
    }

    @Override
    public Author getAuthor(String name) {
        Optional<Author> author = authorRepository.findByName(name);
        return author.orElseGet(Author::new);
    }

    @Transactional
    @Override
    public void removeAuthor(long idAuthor) {
        authorRepository.deleteById(idAuthor);
    }
}
