package com.infy.pinterest.repository.content;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.content.Pin;
import com.infy.pinterest.entity.content.PinLike;
import com.infy.pinterest.entity.user.User;

public interface PinLikeRepository extends JpaRepository<PinLike, Long> {
    List<PinLike> findByPin(Pin pin);
    Optional<PinLike> findByPinAndUser(Pin pin, User user);

    void deleteAllByPin(Pin pin);
}
