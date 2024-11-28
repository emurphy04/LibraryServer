package com.library.server.service;

import com.library.server.model.Author;
import com.library.server.repository.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepo authorRepo;

    @Autowired
    public AuthorService(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }


    public List<Author> getAuthors() {
        return authorRepo.findAll();
    }

    public Optional<Author> getAuthorById(int id) {
        return authorRepo.findById(id);
    }

    public List<Author> getAuthorByLastName(String lastname) {
        List<Author> authors = authorRepo.findAll();
        List<Author> authorsWithLastName = new ArrayList<>();
        for (Author author : authors) {
            if (author.getLastName().equalsIgnoreCase(lastname)) {
                authorsWithLastName.add(author);
            }
        }
        return authorsWithLastName;
    }

    public void addAuthor(Author author) {
        authorRepo.save(author);
    }

    public void deleteAuthor(int id) {
        authorRepo.deleteById(id);
    }
}
