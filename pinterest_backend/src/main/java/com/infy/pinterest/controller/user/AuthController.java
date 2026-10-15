package com.infy.pinterest.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.pinterest.service.user.AuthService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String usernameOrEmail,
            @RequestParam String password,
            @RequestParam(required = false, defaultValue = "0.0.0.0") String ipAddress
    ) {
        String token = authService.login(usernameOrEmail, password, ipAddress);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/logout")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token.replace("Bearer ", ""));
        return ResponseEntity.ok("Logged out successfully");
    }
}
