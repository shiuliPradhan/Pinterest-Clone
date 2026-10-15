package com.infy.pinterest.service.content;


import java.util.List;

import com.infy.pinterest.dto.content.PinSaveDTO;


public interface PinSaveService {
    PinSaveDTO savePin(Long pinId, Long userId, Long boardId);
    List<PinSaveDTO> getSavedPinsByUser(Long userId);
    void removeSave(Long saveId);
}

