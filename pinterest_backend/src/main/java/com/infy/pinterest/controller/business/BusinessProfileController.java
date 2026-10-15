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

import com.infy.pinterest.dto.business.BusinessProfileDTO;
import com.infy.pinterest.service.business.BusinessProfileService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/business")
@RequiredArgsConstructor
public class BusinessProfileController {

    private final BusinessProfileService businessService;

    @PostMapping
    public ResponseEntity<BusinessProfileDTO> create(@RequestBody BusinessProfileDTO dto) {
        return ResponseEntity.ok(businessService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessProfileDTO> update(
            @PathVariable Long id,
            @RequestBody BusinessProfileDTO dto
    ) {
        return ResponseEntity.ok(businessService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessProfileDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(businessService.getById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<BusinessProfileDTO> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(businessService.getByUser(userId));
    }

    @GetMapping
    public ResponseEntity<List<BusinessProfileDTO>> getAll() {
        return ResponseEntity.ok(businessService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        businessService.delete(id);
        return ResponseEntity.ok("Business profile deleted successfully.");
    }
}
