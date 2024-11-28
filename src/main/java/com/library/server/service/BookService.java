package com.library.server.service;

import com.library.server.model.Author;
import com.library.server.model.Book;
import com.library.server.repository.AuthorRepo;
import com.library.server.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;

    @Autowired
    public BookService(BookRepo bookRepo, AuthorRepo authorRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
    }

    public List<Book> getBooks() {
        return bookRepo.findAll();
    }

    public Optional<Book> getBookById(int id) {
        return bookRepo.findById(id);
    }

    public List<Book> getBookByAuthorName(String firstname, String lastname) {
        List<Author> authors = authorRepo.findAll();
        Author currAuthor = new Author();
        for (Author author : authors) {
            if (author.getFirstName().equalsIgnoreCase(firstname) && author.getLastName().equalsIgnoreCase(lastname)) {
                currAuthor = author;
            }
        }
        List<Book> books = bookRepo.findAll();
        for (Book book : books) {
            if (book.getAuthor() == currAuthor) {
                books.add(book);
            }
        }
        return books;
    }

    public void addBook(Book book) {
        bookRepo.save(book);
    }

    public void deleteBook(int id) {
        bookRepo.deleteById(id);
    }
}
