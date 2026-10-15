package com.infy.pinterest.service.content;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.pinterest.entity.content.Pin;
import com.infy.pinterest.entity.content.PinLike;
import com.infy.pinterest.entity.user.User;
import com.infy.pinterest.exception.InfyPinterestException;
import com.infy.pinterest.repository.content.PinLikeRepository;
import com.infy.pinterest.repository.content.PinRepository;
import com.infy.pinterest.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PinLikeServiceImpl implements PinLikeService {

    private final PinLikeRepository repo;
    private final PinRepository pinRepo;
    private final UserRepository userRepo;

    @Override
    @Transactional
    public void like(Long pinId, Long userId) {
        Pin pin = pinRepo.findById(pinId)
                .orElseThrow(() -> new InfyPinterestException("Service.PIN_NOT_FOUND"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        boolean exists = repo.findByPinAndUser(pin, user).isPresent();
        if (exists) {
            // already liked -> ignore or throw based on requirement
            throw new InfyPinterestException("Service.ALREADY_LIKED");
        }

        PinLike pl = new PinLike();
        pl.setPin(pin);
        pl.setUser(user);
        repo.save(pl);
    }

    @Override
    @Transactional
    public void unlike(Long pinId, Long userId) {
        Pin pin = pinRepo.findById(pinId)
                .orElseThrow(() -> new InfyPinterestException("Service.PIN_NOT_FOUND"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        PinLike pl = repo.findByPinAndUser(pin, user)
                .orElseThrow(() -> new InfyPinterestException("Service.NOT_LIKED"));
        repo.delete(pl);
    }

    @Override
    public Long countLikes(Long pinId) {
        Pin pin = pinRepo.findById(pinId)
                .orElseThrow(() -> new InfyPinterestException("Service.PIN_NOT_FOUND"));
        return (long) repo.findByPin(pin).size();
    }
}
