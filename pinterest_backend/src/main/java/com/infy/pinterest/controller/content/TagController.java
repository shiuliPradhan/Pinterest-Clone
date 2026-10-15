package com.infy.pinterest.controller.content;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.pinterest.service.content.TagService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/pin/{pinId}")
    public ResponseEntity<?> addTag(@PathVariable Long pinId, @RequestParam String tag) {
        tagService.addTagToPin(pinId, tag);
        return ResponseEntity.ok("Tag added successfully");
    }

    @GetMapping("/pin/{pinId}")
    public ResponseEntity<List<String>> getTagsByPin(@PathVariable Long pinId) {
        return ResponseEntity.ok(tagService.getTagsByPin(pinId));
    }

    @GetMapping("/{tag}")
    public ResponseEntity<List<Long>> getPinsByTag(@PathVariable String tag) {
        return ResponseEntity.ok(tagService.getPinsByTag(tag));
    }
}
