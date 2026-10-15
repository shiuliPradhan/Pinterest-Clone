package com.infy.pinterest.controller.social;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.pinterest.dto.social.FollowDTO;
import com.infy.pinterest.service.social.FollowService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

	private final FollowService followService;

	@PostMapping("/{followerId}/follow/{followingId}")
	public ResponseEntity<?> follow(@PathVariable Long followerId, @PathVariable Long followingId) {
		followService.follow(followerId, followingId);
		return ResponseEntity.ok("Followed successfully.");
	}

	@DeleteMapping("/{followerId}/unfollow/{followingId}")
	public ResponseEntity<?> unfollow(@PathVariable Long followerId, @PathVariable Long followingId) {
		followService.unfollow(followerId, followingId);
		return ResponseEntity.ok("Unfollowed successfully.");
	}

	@GetMapping("/{userId}/followers")
	public ResponseEntity<List<FollowDTO>> getFollowers(@PathVariable Long userId) {
		return ResponseEntity.ok(followService.getFollowers(userId));
	}

	@GetMapping("/{userId}/following")
	public ResponseEntity<List<FollowDTO>> getFollowing(@PathVariable Long userId) {
		return ResponseEntity.ok(followService.getFollowing(userId));
	}
}