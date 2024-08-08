package com.springboot.blog.repository;

import com.springboot.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // find by email custom jpa query
    Optional<User> findByEmail(String email);

    // find by email or username custom jpa query
    Optional<User> findByUsernameOrEmail(String username, String Email);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
