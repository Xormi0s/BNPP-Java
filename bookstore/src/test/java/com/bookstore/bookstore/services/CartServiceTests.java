package com.bookstore.bookstore.services;

import com.bookstore.bookstore.model.Cart;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.repository.CartRepository;
import com.bookstore.bookstore.service.CartService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTests {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    public void testSaveCart() {
        // Arrange
        Order order1 = new Order("user123", "book123", 2);
        Order order2 = new Order("user123", "book345", 4);
        Cart cart = new Cart("user123", List.of(order1, order2));

        when(cartRepository.save(Mockito.any(Cart.class))).thenReturn(cart);

        // Act
        Cart savedCart = cartService.saveCart(cart);

        // Assert
        Assertions.assertThat(savedCart).isNotNull();
        Assertions.assertThat(savedCart.getUserId()).isEqualTo("user123");
        Assertions.assertThat(savedCart.getOrders().size()).isEqualTo(2);
    }

    @Test
    public void testFindCartById() {
        // Arrange
        Order order = new Order("user123", "book123", 2);
        Cart cart = new Cart("user123", List.of(order));

        when(cartRepository.findByUserId("user123")).thenReturn(Optional.of(cart));

        // Act
        Cart foundCart = cartService.findCartById("user123");

        // Assert
        Assertions.assertThat(foundCart).isNotNull();
        Assertions.assertThat(foundCart.getUserId()).isEqualTo("user123");
        Assertions.assertThat(foundCart.getOrders().size()).isEqualTo(1);
    }
}