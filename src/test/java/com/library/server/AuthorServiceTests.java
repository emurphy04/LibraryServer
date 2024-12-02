package com.library.server;

import com.library.server.model.Author;
import com.library.server.repository.AuthorRepo;
import com.library.server.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorServiceTests {

    @Mock
    private AuthorRepo authorRepo;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAuthors() {
        Author author1 = new Author("John", "Doe");
        Author author2 = new Author("Jane", "Smith");
        when(authorRepo.findAll()).thenReturn(Arrays.asList(author1, author2));

        List<Author> authors = authorService.getAuthors();

        assertEquals(2, authors.size());
        assertEquals("John", authors.get(0).getFirstName());
        assertEquals("Smith", authors.get(1).getLastName());
        verify(authorRepo, times(1)).findAll();
    }

    @Test
    public void testGetAuthorsEmptyList() {
        when(authorRepo.findAll()).thenReturn(List.of());

        List<Author> authors = authorService.getAuthors();

        assertTrue(authors.isEmpty());
        verify(authorRepo, times(1)).findAll();
    }

    @Test
    public void testGetAuthorById() {
        Author author = new Author("John", "Doe");
        when(authorRepo.findById(1)).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.getAuthorById(1);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        verify(authorRepo, times(1)).findById(1);
    }

    @Test
    public void testGetAuthorByIdNotFound() {
        when(authorRepo.findById(1)).thenReturn(Optional.empty());

        Optional<Author> result = authorService.getAuthorById(1);

        assertTrue(result.isEmpty());
        verify(authorRepo, times(1)).findById(1);
    }

    @Test
    public void testAddAuthor() {
        Author author = new Author("John", "Doe");

        authorService.addAuthor(author);

        verify(authorRepo, times(1)).save(author);
    }

    @Test
    public void testAddNullAuthor() {
        Author author = null;
        authorService.addAuthor(author);
        verify(authorRepo, times(1)).save(author);
    }

    @Test
    public void testGetAuthorByLastName() {
        Author author1 = new Author("John", "Doe");
        Author author2 = new Author("Jane", "Smith");
        when(authorRepo.findAll()).thenReturn(Arrays.asList(author1, author2));

        List<Author> result = authorService.getAuthorByLastName("Doe");

        assertEquals(1, result.size());
        assertEquals("Doe", result.get(0).getLastName());
    }

    @Test
    public void testGetAuthorByLastNameNoMatches() {
        Author author1 = new Author("John", "Doe");
        Author author2 = new Author("Jane", "Smith");
        when(authorRepo.findAll()).thenReturn(List.of(author1, author2));

        List<Author> result = authorService.getAuthorByLastName("Brown");

        assertTrue(result.isEmpty());
        verify(authorRepo, times(1)).findAll();
    }
}
