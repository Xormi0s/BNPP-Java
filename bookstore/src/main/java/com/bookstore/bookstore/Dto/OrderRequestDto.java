package com.bookstore.bookstore.Dto;

import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.User;
import lombok.Data;

@Data
public class OrderRequestDto {

    private Book book;
    private User user;
}