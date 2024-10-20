package com.bookstore.bookstore.service;

import com.bookstore.bookstore.model.User;
import com.bookstore.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Method to register a new user by saving the user object in the repository
    public User registerUser(User user) {
        // Save the user and return the saved user object
        return userRepository.save(user);
    }

    // Method to find a user by their username
    public User findByUsername(String username) {
        // Query the repository to find a user with the specified username
        return userRepository.findByUsername(username);
    }
}
