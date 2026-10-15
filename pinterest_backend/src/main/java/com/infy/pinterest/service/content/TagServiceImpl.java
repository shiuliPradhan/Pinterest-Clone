package com.infy.pinterest.service.content;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.pinterest.entity.content.Pin;
import com.infy.pinterest.entity.content.PinTag;
import com.infy.pinterest.entity.content.PinTagMap;
import com.infy.pinterest.exception.InfyPinterestException;
import com.infy.pinterest.repository.content.PinRepository;
import com.infy.pinterest.repository.content.PinTagMapRepository;
import com.infy.pinterest.repository.content.PinTagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final PinTagRepository tagRepo;
    private final PinTagMapRepository tagMapRepo;
    private final PinRepository pinRepo;

    @Override
    @Transactional
    public void addTagToPin(Long pinId, String tagName) {
        Pin pin = pinRepo.findById(pinId)
                .orElseThrow(() -> new InfyPinterestException("Service.PIN_NOT_FOUND"));

        PinTag tag = tagRepo.findByTag(tagName).orElseGet(() -> {
            PinTag t = new PinTag();
            t.setTag(tagName);
            return tagRepo.save(t);
        });

        // avoid duplicate mapping
        boolean exists = tagMapRepo.findByPin(pin).stream()
                .anyMatch(m -> m.getTag().getId().equals(tag.getId()));
        if (!exists) {
            PinTagMap map = new PinTagMap();
            map.setPin(pin);
            map.setTag(tag);
            tagMapRepo.save(map);
        }
    }

    @Override
    public List<String> getTagsByPin(Long pinId) {
        Pin pin = pinRepo.findById(pinId)
                .orElseThrow(() -> new InfyPinterestException("Service.PIN_NOT_FOUND"));

        return tagMapRepo.findByPin(pin).stream()
                .map(m -> m.getTag().getTag())
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getPinsByTag(String tag) {
        PinTag pt = tagRepo.findByTag(tag)
                .orElseThrow(() -> new InfyPinterestException("Service.TAG_NOT_FOUND"));

        return tagMapRepo.findByTag(pt).stream()
                .map(m -> m.getPin().getId())
                .collect(Collectors.toList());
    }
}
