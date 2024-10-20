package com.bookstore.bookstore.service;

import com.bookstore.bookstore.model.Cart;
import com.bookstore.bookstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    // Method to save a Cart to the database
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart); // Saves the provided Cart object in the database and returns the saved cart
    }

    // Method to find a Cart by user ID
    public Cart findCartById(String id) {
        return cartRepository.findByUserId(id).orElse(null); // Finds the Cart by user ID, or returns null if not found
    }
}