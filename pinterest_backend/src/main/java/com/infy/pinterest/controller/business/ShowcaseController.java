package com.infy.pinterest.controller.business;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.pinterest.dto.business.ShowcaseDTO;
import com.infy.pinterest.service.business.ShowcaseService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/showcases")
@RequiredArgsConstructor
public class ShowcaseController {

    private final ShowcaseService showcaseService;

    @PostMapping
    public ResponseEntity<ShowcaseDTO> create(@RequestBody ShowcaseDTO dto) {
        return ResponseEntity.ok(showcaseService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowcaseDTO> update(
            @PathVariable Long id,
            @RequestBody ShowcaseDTO dto
    ) {
        return ResponseEntity.ok(showcaseService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowcaseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(showcaseService.getById(id));
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<ShowcaseDTO>> getByBusiness(@PathVariable Long businessId) {
        return ResponseEntity.ok(showcaseService.getByBusiness(businessId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        showcaseService.delete(id);
        return ResponseEntity.ok("Showcase deleted successfully.");
    }
}
