package com.library.server.service;

import com.library.server.model.Author;
import com.library.server.model.Book;
import com.library.server.repository.AuthorRepo;
import com.library.server.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepo authorRepo;

    private final BookRepo bookRepo;

    @Autowired
    public AuthorService(AuthorRepo authorRepo, BookRepo bookRepo) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
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

    public List<Book> getAuthorBooks(Author author){
        List<Book> books = bookRepo.findAll();
        List<Book> authorsBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor() == author) {
                authorsBooks.add(book);
            }
        }
        return authorsBooks;
    }

    public void addAuthor(Author author) {
        authorRepo.save(author);
    }

    public void deleteAuthor(int id) {
        Author author = authorRepo.findById(id).get();
        List<Book> booksToDelete = getAuthorBooks(author);
        for (Book book : booksToDelete) {
            bookRepo.deleteById(book.getBookId());
        }
        authorRepo.deleteById(id);
    }
}
