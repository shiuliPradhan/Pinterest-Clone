package com.infy.pinterest.repository.social;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.content.Board;
import com.infy.pinterest.entity.social.BoardCollaborator;
import com.infy.pinterest.entity.user.User;

public interface BoardCollaboratorRepository extends JpaRepository<BoardCollaborator, Long> {

    List<BoardCollaborator> findByBoard(Board board);

    List<BoardCollaborator> findByUser(User user);

    Optional<BoardCollaborator> findByBoardAndUser(Board board, User user);

    void deleteAllByBoard(Board board);

    void deleteAllByUser(User user);
}
