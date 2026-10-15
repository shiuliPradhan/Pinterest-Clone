package com.infy.pinterest.service.content;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.pinterest.dto.content.PinDTO;
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
import com.infy.pinterest.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PinServiceImpl implements PinService {

    private final PinRepository pinRepo;
    private final BoardRepository boardRepo;
    private final UserRepository userRepo;
    private final PinMediaRepository pinMediaRepo;
    private final PinTagMapRepository pinTagMapRepo;
    private final PinSaveRepository pinSaveRepo;
    private final PinLikeRepository pinLikeRepo;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public PinDTO create(PinDTO dto) {
        Board board = boardRepo.findById(dto.getBoardId())
                .orElseThrow(() -> new InfyPinterestException("Service.BOARD_NOT_FOUND"));

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        Pin pin = new Pin();
        pin.setBoard(board);
        pin.setUser(user);
        pin.setTitle(dto.getTitle());
        pin.setDescription(dto.getDescription());
        pin.setSourceUrl(dto.getSourceUrl());
        pin.setIsPrivate(dto.getIsPrivate() == null ? false : dto.getIsPrivate());
        pin.setStatus(dto.getStatus() == null ? "PUBLISHED" : dto.getStatus());
        pin.setCreatedAt(LocalDateTime.now());
        pin.setUpdatedAt(LocalDateTime.now());

        Pin saved = pinRepo.save(pin);
        // media, tags, etc. handled by separate services
        return mapper.map(saved, PinDTO.class);
    }

    @Override
    public PinDTO update(Long id, PinDTO dto) {
        Pin p = pinRepo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.PIN_NOT_FOUND"));

        if (dto.getTitle() != null) p.setTitle(dto.getTitle());
        if (dto.getDescription() != null) p.setDescription(dto.getDescription());
        if (dto.getSourceUrl() != null) p.setSourceUrl(dto.getSourceUrl());
        if (dto.getIsPrivate() != null) p.setIsPrivate(dto.getIsPrivate());
        if (dto.getStatus() != null) p.setStatus(dto.getStatus());

        p.setUpdatedAt(LocalDateTime.now());
        pinRepo.save(p);
        return mapper.map(p, PinDTO.class);
    }

    @Override
    public PinDTO getById(Long id) {
        Pin p = pinRepo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.PIN_NOT_FOUND"));
        return mapper.map(p, PinDTO.class);
    }

    @Override
    public List<PinDTO> getByBoard(Long boardId) {
        Board b = boardRepo.findById(boardId)
                .orElseThrow(() -> new InfyPinterestException("Service.BOARD_NOT_FOUND"));
        return pinRepo.findByBoard(b).stream()
                .map(x -> mapper.map(x, PinDTO.class))
                .toList();
    }

    @Override
    public List<PinDTO> searchPins(String keyword) {
        List<Pin> result = new ArrayList<>();
        result.addAll(pinRepo.findByTitleContainingIgnoreCase(keyword));
        result.addAll(pinRepo.findByDescriptionContainingIgnoreCase(keyword));
        return result.stream().distinct()
                .map(p -> mapper.map(p, PinDTO.class))
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Pin p = pinRepo.findById(id)
                .orElseThrow(() -> new InfyPinterestException("Service.PIN_NOT_FOUND"));

        // delete media
        pinMediaRepo.deleteAllByPin(p);
        // delete tag maps
        pinTagMapRepo.deleteAllByPin(p);
        // delete saves
        pinSaveRepo.deleteAllByPin(p);
        // delete likes
        pinLikeRepo.deleteAllByPin(p);
        // delete sponsored pins are handled in business module (ensure sponsored repo deleteAllByPin exists if you want here)
        pinRepo.delete(p);
    }

	@Override
	public List<PinDTO> getAll() {
		return pinRepo.findAll()
	            .stream()
	            .map(p -> mapper.map(p, PinDTO.class))
	            .toList();
	}
}
