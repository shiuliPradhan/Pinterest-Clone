package com.infy.pinterest.controller.content;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.pinterest.dto.content.PinDTO;
import com.infy.pinterest.service.content.PinService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/pins")
@RequiredArgsConstructor
public class PinController {

    private final PinService pinService;
    
    @GetMapping
    public ResponseEntity<List<PinDTO>> getAllPins() {
        return ResponseEntity.ok(pinService.getAll());
    }
    
    @PostMapping
    public ResponseEntity<PinDTO> create(@RequestBody PinDTO dto) {
        return ResponseEntity.ok(pinService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PinDTO> update(@PathVariable Long id, @RequestBody PinDTO dto) {
        return ResponseEntity.ok(pinService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PinDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pinService.getById(id));
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<PinDTO>> getByBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(pinService.getByBoard(boardId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PinDTO>> searchPins(@RequestParam String keyword) {
        return ResponseEntity.ok(pinService.searchPins(keyword));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        pinService.delete(id);
        return ResponseEntity.ok("Pin deleted successfully");
    }
}
