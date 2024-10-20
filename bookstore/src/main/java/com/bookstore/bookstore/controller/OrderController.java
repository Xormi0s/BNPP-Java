package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.Dto.OrderRequestDto;
import com.bookstore.bookstore.model.Book;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.model.User;
import com.bookstore.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/new")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDto orderRequestDTO) {
        //Seperate the Dto object into User and Book for further processing
        User user = orderRequestDTO.getUser();
        Book book = orderRequestDTO.getBook();

        if (user == null) {
            // If no user is logged in, return a 401 Unauthorized status
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Create a new order for the logged-in user with the given book ISBN and a default quantity of 1
        Order order = new Order(user.getId(), book.getIsbn(), 1);

        // Save the order and return it with a 201 Created response status
        return new ResponseEntity<>(orderService.saveOrder(order), HttpStatus.CREATED);
    }


}
