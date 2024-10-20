package com.bookstore.bookstore.Dto;

public class UserDto {
    private String id;
    private String username;

    // Constructor
    public UserDto(String id, String username) {
        this.id = id;
        this.username = username;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
