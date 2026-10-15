package com.infy.pinterest.dto.user;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private String password;
}
