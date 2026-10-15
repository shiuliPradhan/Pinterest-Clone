package com.infy.pinterest.repository.social;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.pinterest.entity.content.Board;
import com.infy.pinterest.entity.social.Invitation;
import com.infy.pinterest.entity.user.User;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByReceiver(User receiver);
    List<Invitation> findBySender(User sender);
    List<Invitation> findByBoard(Board board);

    void deleteAllByBoard(Board board);
}
