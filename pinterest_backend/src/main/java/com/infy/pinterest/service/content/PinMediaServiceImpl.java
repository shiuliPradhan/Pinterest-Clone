package com.infy.pinterest.service.content;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.infy.pinterest.dto.content.PinMediaDTO;
import com.infy.pinterest.entity.content.Pin;
import com.infy.pinterest.entity.content.PinMedia;
import com.infy.pinterest.exception.InfyPinterestException;
import com.infy.pinterest.repository.content.PinMediaRepository;
import com.infy.pinterest.repository.content.PinRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PinMediaServiceImpl implements PinMediaService {

    private final PinMediaRepository repo;
    private final PinRepository pinRepo;
    private final ModelMapper mapper;

    @Override
    public PinMediaDTO addMedia(PinMediaDTO dto) {
        Pin pin = pinRepo.findById(dto.getPinId())
                .orElseThrow(() -> new InfyPinterestException("Service.PIN_NOT_FOUND"));

        PinMedia pm = new PinMedia();
        pm.setPin(pin);
        pm.setMediaUrl(dto.getMediaUrl());
        pm.setMediaType(dto.getMediaType());
        pm.setOrd(dto.getOrd() == null ? 0 : dto.getOrd());

        pm = repo.save(pm);
        return mapper.map(pm, PinMediaDTO.class);
    }

    @Override
    public List<PinMediaDTO> getByPin(Long pinId) {
        Pin pin = pinRepo.findById(pinId)
                .orElseThrow(() -> new InfyPinterestException("Service.PIN_NOT_FOUND"));
        return repo.findByPin(pin).stream()
                .map(x -> mapper.map(x, PinMediaDTO.class))
                .toList();
    }

    @Override
    public void deleteByPin(Long pinId) {
        Pin pin = pinRepo.findById(pinId)
                .orElseThrow(() -> new InfyPinterestException("Service.PIN_NOT_FOUND"));
        repo.deleteAllByPin(pin);
    }
}
