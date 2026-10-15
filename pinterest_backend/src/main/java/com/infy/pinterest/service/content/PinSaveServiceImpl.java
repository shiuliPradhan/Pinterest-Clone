package com.infy.pinterest.service.content;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.infy.pinterest.dto.content.PinSaveDTO;
import com.infy.pinterest.entity.content.Board;
import com.infy.pinterest.entity.content.Pin;
import com.infy.pinterest.entity.content.PinSave;
import com.infy.pinterest.entity.user.User;
import com.infy.pinterest.exception.InfyPinterestException;
import com.infy.pinterest.repository.content.BoardRepository;
import com.infy.pinterest.repository.content.PinRepository;
import com.infy.pinterest.repository.content.PinSaveRepository;
import com.infy.pinterest.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PinSaveServiceImpl implements PinSaveService {

    private final PinSaveRepository repo;
    private final PinRepository pinRepo;
    private final UserRepository userRepo;
    private final BoardRepository boardRepo;
    private final ModelMapper mapper;

    @Override
    public PinSaveDTO savePin(Long pinId, Long userId, Long boardId) {
        Pin pin = pinRepo.findById(pinId)
                .orElseThrow(() -> new InfyPinterestException("Service.PIN_NOT_FOUND"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));
        Board board = null;
        if (boardId != null) {
            board = boardRepo.findById(boardId)
                    .orElseThrow(() -> new InfyPinterestException("Service.BOARD_NOT_FOUND"));
            // optionally ensure board belongs to user if business rule requires
        }

        PinSave ps = new PinSave();
        ps.setPin(pin);
        ps.setUser(user);
        ps.setBoard(board);

        ps = repo.save(ps);
        return mapper.map(ps, PinSaveDTO.class);
    }

    @Override
    public List<PinSaveDTO> getSavedPinsByUser(Long userId) {
        User u = userRepo.findById(userId)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));
        return repo.findByUser(u).stream()
                .map(x -> mapper.map(x, PinSaveDTO.class))
                .toList();
    }

    @Override
    public void removeSave(Long saveId) {
        PinSave ps = repo.findById(saveId)
                .orElseThrow(() -> new InfyPinterestException("Service.SAVE_NOT_FOUND"));
        repo.delete(ps);
    }
}
