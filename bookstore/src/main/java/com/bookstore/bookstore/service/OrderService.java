package com.bookstore.bookstore.service;

import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    // Autowire the OrderRepository to interact with the database
    @Autowired
    private OrderRepository orderRepository;

    // Method to save an Order object to the database
    public Order saveOrder(Order order) {
        // Save the order using the orderRepository and return the saved order
        return orderRepository.save(order);
    }
}