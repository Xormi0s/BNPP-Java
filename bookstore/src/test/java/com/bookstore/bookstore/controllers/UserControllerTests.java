package com.bookstore.bookstore.controllers;

import com.bookstore.bookstore.controller.UserController;
import com.bookstore.bookstore.model.User;
import com.bookstore.bookstore.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testRegisterUser() throws Exception {
        // Arrange
        User user = new User("testUser", "password123");
        when(userService.registerUser(user)).thenReturn(user);

        // Act and Assert
        mockMvc.perform(post("/api/v1/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated())  // Expect HTTP 201
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.password").value("password123"));
    }

    @Test
    public void testLoginUserSuccess() throws Exception {
        // Arrange
        User user = new User("testUser", "password123");
        User existingUser = new User("1", "testUser", "password123");
        when(userService.findByUsername("testUser")).thenReturn(existingUser);

        // Act and Assert
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())  // Expect HTTP 200
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    public void testLoginUserFailure() throws Exception {
        // Arrange
        User user = new User("testUser", "wrongpassword");
        when(userService.findByUsername("testUser")).thenReturn(new User("1", "testUser", "password123"));

        // Act and Assert
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isUnauthorized());  // Expect HTTP 401
    }
}