package com.example.identity.dto;

import com.example.identity.entity.User;

public record UserResponse(String id, String username, String role) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getRole());
    }
}
