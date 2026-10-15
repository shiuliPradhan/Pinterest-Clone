package com.infy.pinterest.repository.social;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.social.Follow;
import com.infy.pinterest.entity.user.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollower(User follower);
    List<Follow> findByFollowing(User following);

    void deleteAllByFollower(User user);
    void deleteAllByFollowing(User user);
}
