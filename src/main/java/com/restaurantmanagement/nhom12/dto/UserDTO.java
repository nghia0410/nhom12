package com.restaurantmanagement.nhom12.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;
    private LocalDateTime createdAt;
}
