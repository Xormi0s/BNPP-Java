package com.bookstore.bookstore.services;

import com.bookstore.bookstore.model.User;
import com.bookstore.bookstore.repository.UserRepository;
import com.bookstore.bookstore.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testRegisterUser() {
        // Arrange
        User user = new User("testUser", "password123");

        when(userRepository.save(user)).thenReturn(user);

        // Act
        User registeredUser = userService.registerUser(user);

        // Assert
        Assertions.assertThat(registeredUser).isNotNull();
        Assertions.assertThat(registeredUser.getUsername()).isEqualTo("testUser");
        Assertions.assertThat(registeredUser.getPassword()).isEqualTo("password123");
    }

    @Test
    public void testFindByUsername() {
        // Arrange
        User user = new User("testUser", "password123");

        when(userRepository.findByUsername("testUser")).thenReturn(user);

        // Act
        User foundUser = userService.findByUsername("testUser");

        // Assert
        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getUsername()).isEqualTo("testUser");
        Assertions.assertThat(foundUser.getPassword()).isEqualTo("password123");
    }
}