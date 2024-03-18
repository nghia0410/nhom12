package com.restaurantmanagement.nhom12.service.implement;

import com.restaurantmanagement.nhom12.dto.SignUpDTO;
import com.restaurantmanagement.nhom12.dto.UserDTO;
import com.restaurantmanagement.nhom12.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IUserService extends UserDetailsService {
    UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;

    User save(SignUpDTO user);

    com.restaurantmanagement.nhom12.entity.User findByEmail(String userEmail);

    void userUpdate(UserDTO userDto, String userEmail);

    boolean checkExistPassword(String password, String userEmail);

    void changePassword(String password, String userEmail);
    List<UserDTO> findAll();

}
