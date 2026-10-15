package com.infy.pinterest.controller.social;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.pinterest.dto.social.InvitationDTO;
import com.infy.pinterest.service.social.InvitationService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping
    public ResponseEntity<InvitationDTO> send(@RequestBody InvitationDTO dto) {
        return ResponseEntity.ok(invitationService.sendInvitation(dto));
    }

    @PostMapping("/{invitationId}/accept")
    public ResponseEntity<InvitationDTO> accept(@PathVariable Long invitationId) {
        return ResponseEntity.ok(invitationService.accept(invitationId));
    }

    @PostMapping("/{invitationId}/decline")
    public ResponseEntity<InvitationDTO> decline(@PathVariable Long invitationId) {
        return ResponseEntity.ok(invitationService.decline(invitationId));
    }

    @GetMapping("/received/{userId}")
    public ResponseEntity<List<InvitationDTO>> getReceived(@PathVariable Long userId) {
        return ResponseEntity.ok(invitationService.getReceived(userId));
    }

    @GetMapping("/sent/{userId}")
    public ResponseEntity<List<InvitationDTO>> getSent(@PathVariable Long userId) {
        return ResponseEntity.ok(invitationService.getSent(userId));
    }
}
