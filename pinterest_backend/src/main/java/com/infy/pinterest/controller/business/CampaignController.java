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

import com.infy.pinterest.dto.business.CampaignDTO;
import com.infy.pinterest.service.business.CampaignService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/campaigns")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @PostMapping
    public ResponseEntity<CampaignDTO> create(@RequestBody CampaignDTO dto) {
        return ResponseEntity.ok(campaignService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampaignDTO> update(
            @PathVariable Long id,
            @RequestBody CampaignDTO dto
    ) {
        return ResponseEntity.ok(campaignService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(campaignService.getById(id));
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<CampaignDTO>> getByBusiness(@PathVariable Long businessId) {
        return ResponseEntity.ok(campaignService.getByBusiness(businessId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        campaignService.delete(id);
        return ResponseEntity.ok("Campaign deleted successfully.");
    }
}