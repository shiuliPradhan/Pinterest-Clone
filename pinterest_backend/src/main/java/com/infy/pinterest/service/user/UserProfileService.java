package com.infy.pinterest.service.user;

import com.infy.pinterest.dto.user.UserProfileDTO;

public interface UserProfileService {
    UserProfileDTO create(UserProfileDTO dto);
    UserProfileDTO update(Long id, UserProfileDTO dto);
    UserProfileDTO getByUser(Long userId);
}
