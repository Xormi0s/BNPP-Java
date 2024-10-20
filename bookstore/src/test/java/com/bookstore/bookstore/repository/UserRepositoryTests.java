package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "password123");
        userRepository.save(user);
    }

    @Test
    public void testFindByUsername() {
        //Act
        User foundUser = userRepository.findByUsername("testUser");

        //Assert
        Assertions.assertThat(foundUser).isNotNull();
        Assertions.assertThat(foundUser.getUsername()).isEqualTo("testUser");
        Assertions.assertThat(foundUser.getPassword()).isEqualTo("password123");

        // Clean up after testing to not affect the application when running
        userRepository.delete(user);
    }
}