package com.example.identity.service;

import com.example.identity.dto.RegisterRequest;
import com.example.identity.dto.UserResponse;
import com.example.identity.entity.User;
import com.example.identity.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @Test
    void registrationStoresBcryptHashAndDoesNotReturnPassword() {
        UserRepository users = mock(UserRepository.class);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        when(users.save(any(User.class))).thenAnswer(call -> call.getArgument(0));

        UserResponse response = new AuthService(users, encoder)
            .register(new RegisterRequest("alice", "secret123"));

        var captured = org.mockito.ArgumentCaptor.forClass(User.class);
        verify(users).save(captured.capture());
        assertTrue(encoder.matches("secret123", captured.getValue().getPassword()));
        assertNotEquals("secret123", captured.getValue().getPassword());
        assertEquals("alice", response.username());
        assertEquals("USER", response.role());
        assertEquals(3, UserResponse.class.getRecordComponents().length);
    }
}
