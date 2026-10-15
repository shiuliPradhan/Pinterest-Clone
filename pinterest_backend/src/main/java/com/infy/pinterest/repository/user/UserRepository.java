package com.infy.pinterest.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}

