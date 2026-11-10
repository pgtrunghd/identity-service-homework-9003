package com.example.identity.repository;

import com.example.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    java.util.Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
