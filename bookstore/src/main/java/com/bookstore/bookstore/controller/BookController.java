package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Handle GET request to fetch all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        // Call the service layer to get all books, and return them in the response with HTTP status 200 (OK)
        return new ResponseEntity<List<Book>>(bookService.getAllBooks(), HttpStatus.OK);
    }

    // Handle GET request to fetch a book by its ISBN
    @GetMapping("/{isbn}")
    public ResponseEntity<Optional<Book>> getBook(@PathVariable String isbn) {
        // Call the service layer to find the book by ISBN, and return it in the response with HTTP status 200 (OK)
        return new ResponseEntity<Optional<Book>>(bookService.getBookByIsbn(isbn), HttpStatus.OK);
    }

    // Handle POST request to create a new book
    @PostMapping("/new")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        // Save the new book using the service layer, and return it in the response with HTTP status 201 (Created)
        return new ResponseEntity<Book>(bookService.saveBook(book), HttpStatus.CREATED);
    }

    // Handle DELETE request to delete a book by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        // Call the service layer to delete the book by its ID
        bookService.deleteBook(id);
        // Return HTTP status 200 (OK) indicating the book was successfully deleted
        return ResponseEntity.ok().build();
    }

}