package com.infy.pinterest.repository.content;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.content.Board;
import com.infy.pinterest.entity.user.User;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByUser(User user);
    List<Board> findByTitleContainingIgnoreCase(String title);
}