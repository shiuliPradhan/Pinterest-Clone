package com.infy.pinterest.service.user;

import com.infy.pinterest.dto.user.UserDTO;

public interface UserService {
    UserDTO register(UserDTO dto);
    UserDTO getById(Long id);
    UserDTO getByUsername(String username);
    UserDTO getByEmail(String email);
    void deleteUser(Long id);
}
