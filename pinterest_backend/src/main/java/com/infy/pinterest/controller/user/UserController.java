package com.infy.pinterest.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.pinterest.dto.user.UserDTO;
import com.infy.pinterest.service.user.UserService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(
            @RequestBody UserDTO dto
    ) {
        UserDTO saved = userService.register(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
