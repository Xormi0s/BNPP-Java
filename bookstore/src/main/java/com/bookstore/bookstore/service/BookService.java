package com.bookstore.bookstore.service;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Method to get all books from the repository
    public List<Book> getAllBooks() {
        return bookRepository.findAll(); // Returns a list of all books from the database
    }

    // Method to get a book by its ISBN number
    public Optional<Book> getBookByIsbn(String id) {
        return bookRepository.findByIsbn(id); // Retrieves a book by its ISBN from the database
    }

    // Method to save a new book or update an existing book in the repository
    public Book saveBook(Book book) {
        return bookRepository.save(book); // Saves the provided book (either new or updated)
    }

    // Method to delete a book by its ID
    public void deleteBook(String id) {
        bookRepository.deleteById(id); // Deletes the book from the repository by its ID
    }
}