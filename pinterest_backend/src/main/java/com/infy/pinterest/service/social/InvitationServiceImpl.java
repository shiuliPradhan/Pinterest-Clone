package com.infy.pinterest.service.social;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.pinterest.dto.social.InvitationDTO;
import com.infy.pinterest.entity.content.Board;
import com.infy.pinterest.entity.social.BoardCollaborator;
import com.infy.pinterest.entity.social.Invitation;
import com.infy.pinterest.entity.user.User;
import com.infy.pinterest.exception.InfyPinterestException;
import com.infy.pinterest.repository.content.BoardRepository;
import com.infy.pinterest.repository.social.BoardCollaboratorRepository;
import com.infy.pinterest.repository.social.InvitationRepository;
import com.infy.pinterest.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepo;
    private final UserRepository userRepo;
    private final BoardRepository boardRepo;
    private final BoardCollaboratorRepository collaboratorRepo;
    private final ModelMapper mapper;

    @Override
    public InvitationDTO sendInvitation(InvitationDTO dto) {

        User sender = userRepo.findById(dto.getSenderId())
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        User receiver = userRepo.findById(dto.getReceiverId())
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        Invitation inv = new Invitation();
        inv.setSender(sender);
        inv.setReceiver(receiver);
        inv.setInvitationType(dto.getInvitationType());
        inv.setMessage(dto.getMessage());
        inv.setStatus("PENDING");

        if (dto.getBoardId() != null) {
            Board board = boardRepo.findById(dto.getBoardId())
                    .orElseThrow(() -> new InfyPinterestException("Service.BOARD_NOT_FOUND"));
            inv.setBoard(board);
        }

        inv = invitationRepo.save(inv);
        return mapper.map(inv, InvitationDTO.class);
    }

    @Override
    @Transactional
    public InvitationDTO accept(Long invitationId) {

        Invitation inv = invitationRepo.findById(invitationId)
                .orElseThrow(() -> new InfyPinterestException("Service.INVITATION_NOT_FOUND"));

        if (!inv.getStatus().equals("PENDING")) {
            throw new InfyPinterestException("Service.INVITATION_ALREADY_PROCESSED");
        }

        inv.setStatus("ACCEPTED");
        invitationRepo.save(inv);

        // If it's board collaboration invite:
        if ("BOARD_COLLAB".equals(inv.getInvitationType())) {

            Board board = inv.getBoard();
            User user = inv.getReceiver();

            // Check if already collaborator
            boolean exists = collaboratorRepo.findByBoard(board).stream()
                    .anyMatch(c -> c.getUser().getId().equals(user.getId()));

            if (!exists) {
                BoardCollaborator collab = new BoardCollaborator();
                collab.setBoard(board);
                collab.setUser(user);
                collab.setRole("CONTRIBUTOR");
                collaboratorRepo.save(collab);
            }
        }

        return mapper.map(inv, InvitationDTO.class);
    }

    @Override
    @Transactional
    public InvitationDTO decline(Long invitationId) {

        Invitation inv = invitationRepo.findById(invitationId)
                .orElseThrow(() -> new InfyPinterestException("Service.INVITATION_NOT_FOUND"));

        if (!inv.getStatus().equals("PENDING")) {
            throw new InfyPinterestException("Service.INVITATION_ALREADY_PROCESSED");
        }

        inv.setStatus("DECLINED");
        invitationRepo.save(inv);

        return mapper.map(inv, InvitationDTO.class);
    }

    @Override
    public List<InvitationDTO> getReceived(Long userId) {

        User receiver = userRepo.findById(userId)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        return invitationRepo.findByReceiver(receiver).stream()
                .map(i -> mapper.map(i, InvitationDTO.class))
                .toList();
    }

    @Override
    public List<InvitationDTO> getSent(Long userId) {

        User sender = userRepo.findById(userId)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        return invitationRepo.findBySender(sender).stream()
                .map(i -> mapper.map(i, InvitationDTO.class))
                .toList();
    }
}
