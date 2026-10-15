package com.infy.pinterest.service.social;

import java.util.List;

import com.infy.pinterest.dto.social.InvitationDTO;

public interface InvitationService {

    InvitationDTO sendInvitation(InvitationDTO dto);
    InvitationDTO accept(Long invitationId);
    InvitationDTO decline(Long invitationId);
    List<InvitationDTO> getReceived(Long userId);
    List<InvitationDTO> getSent(Long userId);
}
