package com.infy.pinterest.controller.business;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.pinterest.dto.business.SponsoredPinDTO;
import com.infy.pinterest.service.business.SponsoredPinService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class SponsoredPinController {

    private final SponsoredPinService sponsoredService;

    @PostMapping
    public ResponseEntity<SponsoredPinDTO> create(@RequestBody SponsoredPinDTO dto) {
        return ResponseEntity.ok(sponsoredService.create(dto));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<SponsoredPinDTO> activate(@PathVariable Long id) {
        return ResponseEntity.ok(sponsoredService.activate(id));
    }

    @PostMapping("/{id}/pause")
    public ResponseEntity<SponsoredPinDTO> pause(@PathVariable Long id) {
        return ResponseEntity.ok(sponsoredService.pause(id));
    }

    @PostMapping("/{id}/click-rate")
    public ResponseEntity<SponsoredPinDTO> updateClickRate(
            @PathVariable Long id,
            @RequestParam Double rate
    ) {
        return ResponseEntity.ok(sponsoredService.updateClickRate(id, rate));
    }

    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<List<SponsoredPinDTO>> getByCampaign(@PathVariable Long campaignId) {
        return ResponseEntity.ok(sponsoredService.getByCampaign(campaignId));
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<SponsoredPinDTO>> getByBusiness(@PathVariable Long businessId) {
        return ResponseEntity.ok(sponsoredService.getByBusiness(businessId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<SponsoredPinDTO>> getActive() {
        return ResponseEntity.ok(sponsoredService.getActive());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        sponsoredService.delete(id);
        return ResponseEntity.ok("Sponsored pin deleted successfully.");
    }
}
