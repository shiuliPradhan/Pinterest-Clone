package com.infy.pinterest.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.user.LoginAttempt;
import com.infy.pinterest.entity.user.User;

import java.util.List;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {
    List<LoginAttempt> findByUserOrderByAttemptTimestampDesc(User user);
}
