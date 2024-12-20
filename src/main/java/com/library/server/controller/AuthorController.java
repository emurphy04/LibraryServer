package com.library.server.controller;

import com.library.server.model.Author;
import com.library.server.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/api/v1/authors")
    public List<Author> getAuthors(){
        return authorService.getAuthors();
    }

    @GetMapping("/api/v1/authors/{id}")
    public Optional<Author> getAuthorById(@PathVariable int id){
        return authorService.getAuthorById(id);
    }

    @GetMapping("/api/v1/authors/lastname={lastname}")
    public List<Author> getAuthorByLastName(@PathVariable String lastname){
        return authorService.getAuthorByLastName(lastname);
    }

    @PostMapping("/api/v1/authors")
    public void addAuthor(@RequestBody Author author){
        authorService.addAuthor(author);
    }

    @DeleteMapping("/api/v1/authors/{id}")
    public void deleteAuthor(@PathVariable int id){
        authorService.deleteAuthor(id);
    }

    @PutMapping("/api/v1/authors/{id}")
    public void updateAuthor(@RequestBody Map<String, Object> body, @PathVariable int id){
        Author currAuthor = authorService.getAuthorById(id).get();
        currAuthor.setFirstName((String) body.get("firstName"));
        currAuthor.setLastName((String) body.get("lastName"));
        authorService.addAuthor(currAuthor);
    }
}
