package com.infy.pinterest.service.content;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.pinterest.dto.content.BoardDTO;
import com.infy.pinterest.entity.content.Board;
import com.infy.pinterest.entity.content.Pin;
import com.infy.pinterest.entity.user.User;
import com.infy.pinterest.exception.InfyPinterestException;
import com.infy.pinterest.repository.content.BoardRepository;
import com.infy.pinterest.repository.content.PinLikeRepository;
import com.infy.pinterest.repository.content.PinMediaRepository;
import com.infy.pinterest.repository.content.PinRepository;
import com.infy.pinterest.repository.content.PinSaveRepository;
import com.infy.pinterest.repository.content.PinTagMapRepository;
import com.infy.pinterest.repository.social.InvitationRepository;
import com.infy.pinterest.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepo;
    private final UserRepository userRepo;
    private final PinRepository pinRepo;
    private final PinMediaRepository pinMediaRepo;
    private final PinTagMapRepository pinTagMapRepo;
    private final PinSaveRepository pinSaveRepo;
    private final PinLikeRepository pinLikeRepo;
    private final InvitationRepository invitationRepo; // to remove invites for board
    private final ModelMapper mapper;

    @Override
    public BoardDTO create(BoardDTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        Board board = new Board();
        board.setUser(user);
        board.setTitle(dto.getTitle());
        board.setDescription(dto.getDescription());
        board.setIsPrivate(dto.getIsPrivate() == null ? false : dto.getIsPrivate());
        board.setCoverImageUrl(dto.getCoverImageUrl());

        Board saved = boardRepo.save(board);
        return mapper.map(saved, BoardDTO.class);
    }

    @Override
    public BoardDTO update(Long id, BoardDTO dto) {
        Board b = boardRepo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.BOARD_NOT_FOUND"));

        if (dto.getTitle() != null) b.setTitle(dto.getTitle());
        if (dto.getDescription() != null) b.setDescription(dto.getDescription());
        if (dto.getIsPrivate() != null) b.setIsPrivate(dto.getIsPrivate());
        if (dto.getCoverImageUrl() != null) b.setCoverImageUrl(dto.getCoverImageUrl());

        boardRepo.save(b);
        return mapper.map(b, BoardDTO.class);
    }

    @Override
    public BoardDTO getById(Long id) {
        Board b = boardRepo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.BOARD_NOT_FOUND"));
        return mapper.map(b, BoardDTO.class);
    }

    @Override
    public List<BoardDTO> getByUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));
        return boardRepo.findByUser(user).stream()
                .map(x -> mapper.map(x, BoardDTO.class))
                .toList();
    }

    @Override
    public List<BoardDTO> searchByTitle(String keyword) {
        return boardRepo.findByTitleContainingIgnoreCase(keyword).stream()
                .map(x -> mapper.map(x, BoardDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Board b = boardRepo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.BOARD_NOT_FOUND"));

        // delete pins and their related child records
        List<Pin> pins = pinRepo.findByBoard(b);
        for (Pin p : pins) {
            // delete pin media
            pinMediaRepo.deleteAllByPin(p);
            // delete tag maps
            pinTagMapRepo.deleteAllByPin(p);
            // delete saves
            pinSaveRepo.deleteAllByPin(p);
            // delete likes
            pinLikeRepo.deleteAllByPin(p);
            // delete sponsored pin relationships are handled by business layer; ensure sponsored repository exists and call from there if needed
            pinRepo.delete(p);
        }

        // delete invitations for this board
        invitationRepo.deleteAllByBoard(b);

        // delete board
        boardRepo.delete(b);
    }
}
