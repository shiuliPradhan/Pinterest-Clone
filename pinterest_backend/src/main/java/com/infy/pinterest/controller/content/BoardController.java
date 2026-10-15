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

import com.infy.pinterest.dto.content.BoardDTO;
import com.infy.pinterest.service.content.BoardService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardDTO> create(@RequestBody BoardDTO dto) {
        return ResponseEntity.ok(boardService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardDTO> update(@PathVariable Long id, @RequestBody BoardDTO dto) {
        return ResponseEntity.ok(boardService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.getById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BoardDTO>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(boardService.getByUser(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BoardDTO>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(boardService.searchByTitle(keyword));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boardService.delete(id);
        return ResponseEntity.ok("Board deleted successfully");
    }
}
