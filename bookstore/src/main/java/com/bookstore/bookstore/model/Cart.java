package com.bookstore.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "carts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    private String id;
    private String userId;
    private List<Order> orders;

    public Cart(String userId, List<Order> orders) {
        this.userId = userId;
        this.orders = orders;
    }

    public void AddCart(Order order){
        this.orders.add(order);
    }
}
