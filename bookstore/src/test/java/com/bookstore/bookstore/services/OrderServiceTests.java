package com.bookstore.bookstore.services;

import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.repository.OrderRepository;
import com.bookstore.bookstore.service.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testSaveOrder() {
        // Arrange
        Order order = new Order(
                "user123",
                "book123",
                2
        );

        when(orderRepository.save(order)).thenReturn(order);

        // Act
        Order savedOrder = orderService.saveOrder(order);

        // Assert
        Assertions.assertThat(savedOrder).isNotNull();
        Assertions.assertThat(savedOrder.getUserId()).isEqualTo("user123");
        Assertions.assertThat(savedOrder.getBookId()).isEqualTo("book123");
        Assertions.assertThat(savedOrder.getQuantity()).isEqualTo(2);
    }
}