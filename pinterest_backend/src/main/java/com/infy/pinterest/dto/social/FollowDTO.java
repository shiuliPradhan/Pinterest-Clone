package com.infy.pinterest.dto.social;

import lombok.Data;

@Data
public class FollowDTO {
    private Long id;
    private Long followerId;
    private Long followingId;
}
