package com.bookstore.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    private String _id;
    private String title;
    private String isbn;
    private Integer pageCount;
    private LocalDateTime publishedDate;
    private String thumbnailUrl;
    private String shortDescription;
    private String longDescription;
    private List<String> authors;
    private List<String> categories;
    private Double price;

    public Book(String title, String isbn, Integer pageCount, LocalDateTime publishedDate, String thumbnailUrl, String shortDescription, String longDescription, List<String> authors, List<String> categories, Double price) {
        this.title = title;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.publishedDate = publishedDate;
        this.thumbnailUrl = thumbnailUrl;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.authors = authors;
        this.categories = categories;
        this.price = price;
    }
}
