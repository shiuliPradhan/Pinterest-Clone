package com.infy.pinterest.service.social;

import java.util.List;

import com.infy.pinterest.dto.social.FollowDTO;

public interface FollowService {
    void follow(Long followerId, Long followingId);
    void unfollow(Long followerId, Long followingId);
    List<FollowDTO> getFollowers(Long userId);
    List<FollowDTO> getFollowing(Long userId);
}
