package com.infy.pinterest.service.user;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.infy.pinterest.config.JwtTokenProvider;
import com.infy.pinterest.entity.user.LoginAttempt;
import com.infy.pinterest.entity.user.User;
import com.infy.pinterest.exception.InfyPinterestException;
import com.infy.pinterest.repository.user.LoginAttemptRepository;
import com.infy.pinterest.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final LoginAttemptRepository loginAttemptRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // login returns JWT token
    @Override
    public String login(String usernameOrEmail, String password, String ipAddress) {

        User user = userRepo.findByUsername(usernameOrEmail)
                .or(() -> userRepo.findByEmail(usernameOrEmail))
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        boolean matches = passwordEncoder.matches(password, user.getPassword());

        // record login attempt
        LoginAttempt attempt = LoginAttempt.builder()
                .user(user)
                .success(matches)
                .ipAddress(ipAddress)
                .attemptTimestamp(LocalDateTime.now())
                .build();
        loginAttemptRepo.save(attempt);

        if (!matches) {
            throw new InfyPinterestException("Service.INVALID_CREDENTIALS");
        }

        // generate JWT token (JwtTokenProvider assumed to exist)
        String token = jwtTokenProvider.generateToken(user.getUsername(), user.getRoles());
        return token;
    }

    @Override
    public void logout(String token) {
        // Implement token invalidation if you maintain server-side session or blacklist.
        // If you use sessions table, you can store and remove tokens there.
        // Here we assume JwtTokenProvider handles token validation and blacklist; if not, add SessionRepository logic.
        jwtTokenProvider.invalidateToken(token); // implement this method in your provider or replace with your logic.
    }
}
