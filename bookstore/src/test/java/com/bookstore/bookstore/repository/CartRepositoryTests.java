package com.bookstore.bookstore.repository;

import com.bookstore.bookstore.model.Cart;
import com.bookstore.bookstore.model.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Arrays;
import java.util.Optional;

@DataMongoTest
public class CartRepositoryTests {

    @Autowired
    private CartRepository cartRepository;

    private Cart cart;
    private Order order1;
    private Order order2;

    @BeforeEach
    void setUp() {
        order1 = new Order("user123", "book123", 2);
        order2 = new Order("user123", "book456", 1);

        cart = new Cart("user123", Arrays.asList(order1, order2));

        cartRepository.save(cart);
    }

    @Test
    public void testFindByUserId() {

        // Act
        Optional<Cart> result = cartRepository.findByUserId("user123");

        // Assert
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getUserId()).isEqualTo("user123");
        Assertions.assertThat(result.get().getOrders()).hasSize(2);
        Assertions.assertThat(result.get().getOrders().get(0).getBookId()).isEqualTo("book123");
        Assertions.assertThat(result.get().getOrders().get(0).getQuantity()).isEqualTo(2);
        Assertions.assertThat(result.get().getOrders().get(1).getBookId()).isEqualTo("book456");
        Assertions.assertThat(result.get().getOrders().get(1).getQuantity()).isEqualTo(1);

        // Clean up after testing to not affect the application when running
        cartRepository.delete(cart);
    }
}
