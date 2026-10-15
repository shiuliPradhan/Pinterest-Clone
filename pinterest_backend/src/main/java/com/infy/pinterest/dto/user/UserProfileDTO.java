package com.infy.pinterest.dto.user;

import lombok.Data;

@Data
public class UserProfileDTO {
    private Long id;
    private Long userId;
    private String displayName;
    private String bio;
    private String profileImageUrl;
    private String phoneNumber;
    private String websiteUrl;
    private String location;
}

