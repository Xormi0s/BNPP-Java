package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.model.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@DataMongoTest
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(
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

        bookRepository.save(book);
    }

    @Test
    public void testFindByIsbn() {

        // Act
        Optional<Book> result = bookRepository.findByIsbn("1234567890");

        // Assert
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getTitle()).isEqualTo("Spring Boot in Action");

        // Clean up after testing to not affect the application when running
        bookRepository.delete(book);
    }
}
