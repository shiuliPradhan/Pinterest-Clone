package com.infy.pinterest.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.pinterest.service.user.PasswordResetService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService resetService;

    @PostMapping("/reset-request/{userId}")
    public ResponseEntity<?> generateToken(@PathVariable Long userId) {
        resetService.generateResetToken(userId);
        return ResponseEntity.ok("Password reset token generated & SMS/Email sent.");
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<?> validate(@PathVariable String token) {
        boolean valid = resetService.validateToken(token);
        return ResponseEntity.ok(valid ? "Valid Token" : "Invalid Token");
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword
    ) {
        resetService.verifyMobileAndResetPassword(token, newPassword);
        return ResponseEntity.ok("Password updated successfully.");
    }
}
