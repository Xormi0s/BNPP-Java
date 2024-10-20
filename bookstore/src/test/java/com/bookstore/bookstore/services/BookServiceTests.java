package com.bookstore.bookstore.services;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.repository.BookRepository;
import com.bookstore.bookstore.service.BookService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testAddBook() {
        Book book = new Book(
                "Spring Boot in Action",
                "1234567890",
                300,
                LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0),
                "http://example.com/thumbnail.jpg",
                "Short description",
                "Long description",
                Arrays.asList("Author One", "Author Two"),
                Arrays.asList("Category One", "Category Two"),
                29.99
        );

        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        Book savedBook = bookService.saveBook(book);

        Assertions.assertThat(savedBook).isNotNull();
    }

    @Test
    public void testGetAllBooks() {
        // Arrange
        Book book1 = new Book(
                "Spring Boot in Action",
                "1234567890",
                300,
                LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0),
                "http://example.com/thumbnail.jpg",
                "Short description",
                "Long description",
                Arrays.asList("Author One"),
                Arrays.asList("Category One"),
                29.99
        );
        Book book2 = new Book(
                "Spring Boot 2.0",
                "0987654321",
                350,
                LocalDateTime.of(2021, 6, 15, 0, 0, 0, 0),
                "http://example.com/thumbnail2.jpg",
                "Another short description",
                "Another long description",
                Arrays.asList("Author Three"),
                Arrays.asList("Category Two"),
                39.99
        );

        List<Book> books = Arrays.asList(book1, book2);
        when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<Book> bookList = bookService.getAllBooks();

        // Assert
        Assertions.assertThat(bookList).isNotEmpty();
        Assertions.assertThat(bookList.size()).isEqualTo(2);
    }

    @Test
    public void testGetBookByIsbn() {
        // Arrange
        Book book = new Book(
                "Spring Boot in Action",
                "1234567890",
                300,
                LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0),
                "http://example.com/thumbnail.jpg",
                "Short description",
                "Long description",
                Arrays.asList("Author One"),
                Arrays.asList("Category One"),
                29.99
        );
        when(bookRepository.findByIsbn("1234567890")).thenReturn(Optional.of(book));

        // Act
        Optional<Book> foundBook = bookService.getBookByIsbn("1234567890");

        // Assert
        Assertions.assertThat(foundBook).isPresent();
        Assertions.assertThat(foundBook.get().getIsbn()).isEqualTo("1234567890");
    }

    @Test
    public void testDeleteBook() {
        // Arrange
        Book book = new Book(
                "Spring Boot in Action",
                "1234567890",
                300,
                LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0),
                "http://example.com/thumbnail.jpg",
                "Short description",
                "Long description",
                Arrays.asList("Author One"),
                Arrays.asList("Category One"),
                29.99
        );

        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        // Act
        Book savedBook = bookService.saveBook(book);
        Assertions.assertThat(savedBook).isNotNull();

        bookService.deleteBook(savedBook.get_id());

        // Assert
        verify(bookRepository, times(1)).deleteById(savedBook.get_id());
    }
}