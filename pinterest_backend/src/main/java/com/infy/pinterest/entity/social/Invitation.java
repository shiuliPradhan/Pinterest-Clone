package com.infy.pinterest.entity.social;


import com.infy.pinterest.entity.content.Board;
import com.infy.pinterest.entity.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
@Table(name = "invitations")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // sender (inviter)
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    // receiver (invitee)
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    // for board collaboration
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private String invitationType;   // BOARD_COLLAB | USER_CONNECT
    private String message;
    private String status;           // PENDING | ACCEPTED | DECLINED
}
