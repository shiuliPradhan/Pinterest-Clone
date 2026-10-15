package com.infy.pinterest.controller.content;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.pinterest.service.content.PinLikeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class PinLikeController {

    private final PinLikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<?> like(
            @RequestParam Long pinId,
            @RequestParam Long userId
    ) {
        likeService.like(pinId, userId);
        return ResponseEntity.ok("Pin liked.");
    }

    @PostMapping("/unlike")
    public ResponseEntity<?> unlike(
            @RequestParam Long pinId,
            @RequestParam Long userId
    ) {
        likeService.unlike(pinId, userId);
        return ResponseEntity.ok("Pin unliked.");
    }

    @GetMapping("/count/{pinId}")
    public ResponseEntity<Long> count(@PathVariable Long pinId) {
        return ResponseEntity.ok(likeService.countLikes(pinId));
    }
}
