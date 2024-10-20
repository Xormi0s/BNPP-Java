package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.Dto.UserDto;
import com.bookstore.bookstore.model.User;
import com.bookstore.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // Register a new user and return the created user with status 201
        return new ResponseEntity<User>(userService.registerUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody User user) {
        // Try to find an existing user by username
        User existingUser = userService.findByUsername(user.getUsername());

        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            // If the user is found and password matches, create a UserDto and return with status 200
            UserDto userDto = new UserDto(existingUser.getId(), existingUser.getUsername());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }

        // If user not found or password mismatch, return Unauthorized status 401
        return ResponseEntity.status(401).build();
    }
}