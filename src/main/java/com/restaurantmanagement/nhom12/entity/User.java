package com.restaurantmanagement.nhom12.entity;

import com.restaurantmanagement.nhom12.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")

    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
    @Column(name="create_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    public UserDTO convertUserDto(){
        UserDTO userDto = new UserDTO();
        userDto.setId(this.id);
        userDto.setUsername(this.username);
        userDto.setEmail(this.email);
        userDto.setCreatedAt(this.createdAt);
        userDto.setRole(this.role.getRoleName());
        return userDto;
    }
}
