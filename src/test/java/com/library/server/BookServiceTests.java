package com.library.server;

import com.library.server.model.Author;
import com.library.server.model.Book;
import com.library.server.repository.BookRepo;
import com.library.server.repository.AuthorRepo;
import com.library.server.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTests {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private AuthorRepo authorRepo;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBooks() {
        Book book1 = new Book("Book One", new Author("John", "Doe"), "2022-01-01");
        Book book2 = new Book("Book Two", new Author("Jane", "Smith"), "2023-01-01");
        when(bookRepo.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getBooks();

        assertEquals(2, books.size());
        assertEquals("Book One", books.get(0).getName());
        assertEquals(LocalDate.parse("2023-01-01"), books.get(1).getReleaseDate());
        verify(bookRepo, times(1)).findAll();
    }

    @Test
    public void testGetBooksEmptyList() {
        when(bookRepo.findAll()).thenReturn(List.of());

        List<Book> books = bookService.getBooks();

        assertTrue(books.isEmpty());
        verify(bookRepo, times(1)).findAll();
    }

    @Test
    public void testGetBookById() {
        Book book = new Book("Book One", new Author("John", "Doe"), "2022-01-01");
        when(bookRepo.findById(1)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(1);

        assertTrue(result.isPresent());
        assertEquals("Book One", result.get().getName());
        verify(bookRepo, times(1)).findById(1);
    }

    @Test
    public void testGetBookByIdNotFound() {
        when(bookRepo.findById(1)).thenReturn(Optional.empty());

        Optional<Book> result = bookService.getBookById(1);

        assertTrue(result.isEmpty());
        verify(bookRepo, times(1)).findById(1);
    }

    @Test
    public void testAddBook() {
        Book book = new Book("Book One", new Author("John", "Doe"), "2022-01-01");

        bookService.addBook(book);

        verify(bookRepo, times(1)).save(book);
    }

    @Test
    public void testAddNullBook() {
        Book book = null;
        bookService.addBook(book);
        verify(bookRepo, times(1)).save(book);
    }

    @Test
    public void testAddBookWithNonexistentAuthor() {
        Book book = new Book("Book One", null, "2022-01-01");
        bookService.addBook(book);
        verify(bookRepo, times(1)).save(book);
    }

    @Test
    public void testDeleteBook() {
        bookService.deleteBook(1);

        verify(bookRepo, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteBookNotFound() {
        doThrow(new IllegalArgumentException("No such book")).when(bookRepo).deleteById(999);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> bookService.deleteBook(999));

        assertEquals("No such book", exception.getMessage());
        verify(bookRepo, times(1)).deleteById(999);
    }

    @Test
    public void testGetBookByAuthorName() {
        Author author = new Author("John", "Doe");
        Book book1 = new Book("Book One", author, "2022-01-01");
        when(authorRepo.findAll()).thenReturn(List.of(author));
        when(bookRepo.findAll()).thenReturn(List.of(book1));

        List<Book> result = bookService.getBookByAuthorName("John-Doe");

        assertEquals(1, result.size());
        assertEquals("Book One", result.get(0).getName());
    }

    @Test
    public void testGetBookByAuthorNameNoMatches() {
        Author author = new Author("John", "Doe");
        Book book = new Book("Book One", author, "2022-01-01");
        when(authorRepo.findAll()).thenReturn(List.of(author));
        when(bookRepo.findAll()).thenReturn(List.of(book));

        List<Book> result = bookService.getBookByAuthorName("Jane-Smith");

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetBookByAuthorNameMalformedName() {
        List<Book> result = bookService.getBookByAuthorName("MalformedName");
        assertTrue(result.isEmpty());
    }
}
