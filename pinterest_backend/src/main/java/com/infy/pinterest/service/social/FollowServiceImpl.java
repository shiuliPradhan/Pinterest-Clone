package com.infy.pinterest.service.social;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.infy.pinterest.dto.social.FollowDTO;
import com.infy.pinterest.entity.social.Follow;
import com.infy.pinterest.entity.user.User;
import com.infy.pinterest.exception.InfyPinterestException;
import com.infy.pinterest.repository.social.FollowRepository;
import com.infy.pinterest.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepo;
    private final UserRepository userRepo;
    private final ModelMapper mapper;

    @Override
    public void follow(Long followerId, Long followingId) {

        if (followerId.equals(followingId)) {
            throw new InfyPinterestException("Service.CANNOT_FOLLOW_SELF");
        }

        User follower = userRepo.findById(followerId)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));
        User following = userRepo.findById(followingId)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        boolean exists = followRepo.findByFollower(follower).stream()
                .anyMatch(f -> f.getFollowing().getId().equals(followingId));

        if (exists) {
            throw new InfyPinterestException("Service.ALREADY_FOLLOWING");
        }

        Follow f = new Follow();
        f.setFollower(follower);
        f.setFollowing(following);

        followRepo.save(f);
    }

    @Override
    public void unfollow(Long followerId, Long followingId) {

        User follower = userRepo.findById(followerId)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));
        @SuppressWarnings("unused")
		User following = userRepo.findById(followingId)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        Follow existing = followRepo.findByFollower(follower).stream()
                .filter(f -> f.getFollowing().getId().equals(followingId))
                .findFirst()
                .orElseThrow(() -> new InfyPinterestException("Service.NOT_FOLLOWING"));

        followRepo.delete(existing);
    }

    @Override
    public List<FollowDTO> getFollowers(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        return followRepo.findByFollowing(user).stream()
                .map(f -> mapper.map(f, FollowDTO.class))
                .toList();
    }

    @Override
    public List<FollowDTO> getFollowing(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new InfyPinterestException("Service.USER_NOT_FOUND"));

        return followRepo.findByFollower(user).stream()
                .map(f -> mapper.map(f, FollowDTO.class))
                .toList();
    }
}
