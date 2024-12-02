package com.library.server.controller;

import com.library.server.model.Author;
import com.library.server.model.Book;
import com.library.server.service.AuthorService;
import com.library.server.service.BookService;
import com.library.server.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/api/v1/books")
    public List<Book> getBooks(){
        return bookService.getBooks();
    }

    @GetMapping("/api/v1/books/{id}")
    public Optional<Book> getBookById(@PathVariable int id){
        return bookService.getBookById(id);
    }

    @GetMapping("/api/v1/books/author/{authorname}")
    public List<Book> getBookByAuthor(@PathVariable String authorname){
        return bookService.getBookByAuthorName(authorname);
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/api/v1/books")
    public void addBook(@RequestBody Map<String, Object> bookdetails, Book book){
        int id = (int) bookdetails.get("author");
        book.setAuthor(authorService.getAuthorById(id).get());
        book.setName((String) bookdetails.get("name"));
        book.setReleaseDate((String) bookdetails.get("release_date"));
        bookService.addBook(book);
    }

    @DeleteMapping("/api/v1/books/{id}")
    public void deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
    }

    @PutMapping("/api/v1/books/{id}")
    public void updateBook(@RequestBody Map<String, Object> body, @PathVariable int id){
        Book currBook = bookService.getBookById(id).get();
        currBook.setAuthor(authorService.getAuthorById((Integer) body.get("author")).get());
        currBook.setName((String) body.get("name"));
        currBook.setReleaseDate((String) body.get("release_date"));
        bookService.addBook(currBook);
    }
}
