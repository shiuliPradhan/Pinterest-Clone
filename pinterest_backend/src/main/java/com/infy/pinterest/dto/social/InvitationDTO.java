package com.infy.pinterest.dto.social;


import lombok.Data;

@Data
public class InvitationDTO {

    private Long id;

    private Long senderId;
    private Long receiverId;

    private Long boardId;     // null if user connection

    private String invitationType;   // BOARD_COLLAB | USER_CONNECT

    private String message;
    private String status;          // PENDING | ACCEPTED | DECLINED
}

