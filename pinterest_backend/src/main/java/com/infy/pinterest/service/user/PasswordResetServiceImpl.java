package com.infy.pinterest.service.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.infy.pinterest.entity.user.PasswordResetToken;
import com.infy.pinterest.entity.user.User;
import com.infy.pinterest.exception.InfyPinterestException;
import com.infy.pinterest.repository.user.PasswordResetTokenRepository;
import com.infy.pinterest.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final PasswordResetTokenRepository tokenRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void generateResetToken(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        // create token (UUID) and expiry (e.g., 15 minutes)
        String token = UUID.randomUUID().toString();
        PasswordResetToken prt = new PasswordResetToken();
        prt.setUser(user);
        prt.setToken(token);
        prt.setMobileVerified(false);
        prt.setCreatedAt(LocalDateTime.now());
        prt.setExpiresAt(LocalDateTime.now().plusMinutes(15));

        tokenRepo.save(prt);

        // TODO: integrate SMS/email sending to user.getPhoneNumber() or user.getEmail()
        // For now, return token to caller via logs or UI (not secure for prod)
    }

    @Override
    public boolean validateToken(String token) {
        PasswordResetToken prt = tokenRepo.findByToken(token)
                .orElseThrow(() -> new InfyPinterestException("Service.TOKEN_NOT_FOUND"));

        if (prt.getExpiresAt() != null && prt.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InfyPinterestException("Service.TOKEN_EXPIRED");
        }
        return true;
    }

    @Override
    public void verifyMobileAndResetPassword(String token, String newPassword) {
        PasswordResetToken prt = tokenRepo.findByToken(token)
                .orElseThrow(() -> new InfyPinterestException("Service.TOKEN_NOT_FOUND"));

        if (prt.getExpiresAt() != null && prt.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InfyPinterestException("Service.TOKEN_EXPIRED");
        }

        // ensure mobile verified flag (in production you'd verify OTP)
        if (!Boolean.TRUE.equals(prt.getMobileVerified())) {
            throw new InfyPinterestException("Service.MOBILE_NOT_VERIFIED");
        }

        User user = prt.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        // Invalidate token(s)
        tokenRepo.deleteAllByUser(user);
    }
}