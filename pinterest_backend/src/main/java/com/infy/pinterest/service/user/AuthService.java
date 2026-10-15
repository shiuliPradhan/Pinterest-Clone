package com.infy.pinterest.service.user;

public interface AuthService {
    String login(String usernameOrEmail, String password, String ipAddress);
    void logout(String token);
}
