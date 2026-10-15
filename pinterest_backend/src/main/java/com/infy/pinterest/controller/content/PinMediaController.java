package com.infy.pinterest.controller.content;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.pinterest.dto.content.PinMediaDTO;
import com.infy.pinterest.service.content.PinMediaService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/pin-media")
@RequiredArgsConstructor
public class PinMediaController {

    private final PinMediaService mediaService;

    @PostMapping
    public ResponseEntity<PinMediaDTO> addMedia(@RequestBody PinMediaDTO dto) {
        return ResponseEntity.ok(mediaService.addMedia(dto));
    }

    @GetMapping("/pin/{pinId}")
    public ResponseEntity<List<PinMediaDTO>> getByPin(@PathVariable Long pinId) {
        return ResponseEntity.ok(mediaService.getByPin(pinId));
    }

    @DeleteMapping("/pin/{pinId}")
    public ResponseEntity<?> deleteByPin(@PathVariable Long pinId) {
        mediaService.deleteByPin(pinId);
        return ResponseEntity.ok("Media deleted for pin.");
    }
}
