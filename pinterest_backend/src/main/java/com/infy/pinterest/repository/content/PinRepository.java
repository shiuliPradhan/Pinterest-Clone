package com.infy.pinterest.repository.content;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.content.Board;
import com.infy.pinterest.entity.content.Pin;
import com.infy.pinterest.entity.user.User;

import java.util.List;

public interface PinRepository extends JpaRepository<Pin, Long> {
    List<Pin> findByBoard(Board board);
    List<Pin> findByUser(User user);

    List<Pin> findByTitleContainingIgnoreCase(String title);
    List<Pin> findByDescriptionContainingIgnoreCase(String description);
}
