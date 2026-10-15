package com.infy.pinterest.service.content;

import java.util.List;

import com.infy.pinterest.dto.content.PinMediaDTO;

public interface PinMediaService {
    PinMediaDTO addMedia(PinMediaDTO dto);
    List<PinMediaDTO> getByPin(Long pinId);
    void deleteByPin(Long pinId);
}
