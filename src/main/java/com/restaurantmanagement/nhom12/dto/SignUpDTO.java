package com.restaurantmanagement.nhom12.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUpDTO {
    @NotNull(message = "Mật khẩu không được để trống.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&*!.,])[A-Za-z\\d@#$%^&*!.,]{8,}$"
            ,message = "Mật khẩu phải có ít nhất 8 ký tự, 1 chữ thường, 1 chữ in hoa, 1 số, 1 chữ cái đặc biệt")
    private String password;
    @NotNull(message = "Email không được để trống.")
    @Email(message = "Email không đúng định dạng.")
    private String email;
    @NotNull(message = "Username không được để trống.")
    @Pattern(regexp = "^\\p{L}+\\s*(\\p{L}+)*\\s*\\p{L}+$", message = "Tên đăng nhập không đúng định dạng.")
    private String Username;
}
