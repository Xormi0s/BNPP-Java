package com.bookstore.bookstore.controller;

import com.bookstore.bookstore.model.Cart;
import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    // Handle POST request to create a new cart
    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        // Save the new cart and return it in the response with HTTP status 201 (Created)
        return new ResponseEntity<Cart>(cartService.saveCart(cart), HttpStatus.CREATED);
    }

    // Handle GET request to retrieve a cart by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable String id) {
        Cart cart = cartService.findCartById(id);  // Fetch the cart by its ID
        if (cart != null) {
            // If the cart exists, return it with HTTP status 200 (OK)
            return ResponseEntity.ok(cart);
        } else {
            // If the cart is not found, return HTTP status 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }

    // Handle POST request to add an order to the cart
    @PostMapping("/addOrder")
    public ResponseEntity<Cart> addOrder(@RequestBody Order order) {
        if(order != null) {
            // Find the cart associated with the user's ID
            Cart cart = cartService.findCartById(order.getUserId());
            if (cart != null) {
                // If the cart exists, add the order to it and save the updated cart
                cart.AddCart(order);
                cartService.saveCart(cart);
                return ResponseEntity.ok(cart);  // Return the updated cart with HTTP status 200 (OK)
            } else {
                // If the cart does not exist, create a new cart for the user and add the order to it
                List<Order> orderList = new ArrayList<>();
                orderList.add(order);
                cart = new Cart(order.getUserId(), orderList);
                cartService.saveCart(cart);  // Save the newly created cart
            }
        }
        // If the order or cart could not be processed, return HTTP status 404 (Not Found)
        return ResponseEntity.notFound().build();
    }

    // Handle DELETE request to remove an order from the cart
    @DeleteMapping("/{cartId}/removeOrder/{orderId}")
    public ResponseEntity<Cart> removeOrderFromCart(@PathVariable String cartId, @PathVariable String orderId) {
        Cart cart = cartService.findCartById(cartId);  // Find the cart by its ID
        if (cart != null) {
            // If the cart exists, remove the order with the specified order ID from the cart's orders
            cart.getOrders().removeIf(order -> order.getId().equals(orderId));
            cartService.saveCart(cart);  // Save the updated cart
            return ResponseEntity.ok(cart);  // Return the updated cart with HTTP status 200 (OK)
        }
        // If the cart is not found, return HTTP status 404 (Not Found)
        return ResponseEntity.notFound().build();
    }

    // Handle PUT request to update the quantity of an order in the cart
    @PutMapping("/{cartId}/updateOrderQuantity/{orderId}")
    public ResponseEntity<Cart> updateOrderQuantity(@PathVariable String cartId, @PathVariable String orderId, @RequestParam int quantity) {
        Cart cart = cartService.findCartById(cartId);  // Find the cart by its ID
        if (cart != null) {
            // If the cart exists, find the order by its ID and update its quantity
            cart.getOrders().stream()
                    .filter(order -> order.getId().equals(orderId))
                    .forEach(order -> order.setQuantity(quantity));  // Set the new quantity for the order
            cartService.saveCart(cart);  // Save the updated cart
            return ResponseEntity.ok(cart);  // Return the updated cart with HTTP status 200 (OK)
        }
        // If the cart is not found, return HTTP status 404 (Not Found)
        return ResponseEntity.notFound().build();
    }
}
