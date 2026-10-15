package com.infy.pinterest.service.user;

public interface PasswordResetService {
    void generateResetToken(Long userId);
    boolean validateToken(String token);
    void verifyMobileAndResetPassword(String token, String newPassword);
}
