package com.infy.pinterest.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.user.UserProfile;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserId(Long userId);
}