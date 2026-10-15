package com.infy.pinterest.service.content;

public interface PinLikeService {
    void like(Long pinId, Long userId);
    void unlike(Long pinId, Long userId);
    Long countLikes(Long pinId);
}
