package com.infy.pinterest.service.content;

import java.util.List;

import com.infy.pinterest.dto.content.PinDTO;

public interface PinService {
    PinDTO create(PinDTO dto);
    PinDTO update(Long id, PinDTO dto);
    PinDTO getById(Long id);
    List<PinDTO> getByBoard(Long boardId);
    List<PinDTO> searchPins(String keyword);
    void delete(Long id);
    List<PinDTO> getAll();
}
