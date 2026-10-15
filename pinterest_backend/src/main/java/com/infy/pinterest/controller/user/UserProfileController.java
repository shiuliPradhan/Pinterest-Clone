package com.infy.pinterest.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.pinterest.dto.user.UserProfileDTO;
import com.infy.pinterest.service.user.UserProfileService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService profileService;

    @PostMapping
    public ResponseEntity<UserProfileDTO> create(@RequestBody UserProfileDTO dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }

    @PutMapping("/{profileId}") 
    public ResponseEntity<UserProfileDTO> update(
            @PathVariable Long profileId,
            @RequestBody UserProfileDTO dto
    ) {
        return ResponseEntity.ok(profileService.update(profileId, dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserProfileDTO> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getByUser(userId));
    }
}
