package com.library.server.controller;

import com.library.server.model.Book;
import com.library.server.service.BookService;
import com.library.server.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/v1/books")
    public List<Book> getBooks(){
        return bookService.getBooks();
    }

    @GetMapping("/api/v1/books/{id}")
    public Optional<Book> getBookById(@PathVariable int id){
        return bookService.getBookById(id);
    }

    @GetMapping("/api/v1/books/author+firstname={firstname}+lastname={lastname}")
    public List<Book> getBookByAuthor(@PathVariable String firstname, @PathVariable String lastname){
        return bookService.getBookByAuthorName(firstname, lastname);
    }

    @PostMapping("/api/v1/books")
    public void addBook(@RequestBody Book book){
        bookService.addBook(book);
    }

    @DeleteMapping("/api/v1/books/{id}")
    public void deleteBook(@PathVariable int id){
        bookService.deleteBook(id);
    }

    @PutMapping("/api/v1/books/{id}")
    public void updateBook(@PathVariable int id){

    }
}
