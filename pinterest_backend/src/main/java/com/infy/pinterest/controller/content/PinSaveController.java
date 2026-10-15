package com.infy.pinterest.controller.content;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.pinterest.dto.content.PinSaveDTO;
import com.infy.pinterest.service.content.PinSaveService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/saved")
@RequiredArgsConstructor
public class PinSaveController {

    private final PinSaveService saveService;

    @PostMapping
    public ResponseEntity<PinSaveDTO> savePin(
            @RequestParam Long pinId,
            @RequestParam Long userId,
            @RequestParam(required = false) Long boardId
    ) {
        return ResponseEntity.ok(saveService.savePin(pinId, userId, boardId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PinSaveDTO>> getSavedPins(@PathVariable Long userId) {
        return ResponseEntity.ok(saveService.getSavedPinsByUser(userId));
    }

    @DeleteMapping("/{saveId}")
    public ResponseEntity<?> deleteSave(@PathVariable Long saveId) {
        saveService.removeSave(saveId);
        return ResponseEntity.ok("Saved pin removed.");
    }
}