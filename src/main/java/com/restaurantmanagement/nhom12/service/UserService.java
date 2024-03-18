package com.restaurantmanagement.nhom12.service;

import com.restaurantmanagement.nhom12.dto.SignUpDTO;
import com.restaurantmanagement.nhom12.dto.UserDTO;
import com.restaurantmanagement.nhom12.entity.User;
import com.restaurantmanagement.nhom12.repository.IRoleRepository;
import com.restaurantmanagement.nhom12.repository.IUserRepository;
import com.restaurantmanagement.nhom12.service.implement.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy dữ liệu tài khoản");
        }
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleName());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                List.of(authority));
    }

    @Override
    public User save(SignUpDTO user) {
        try {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new RuntimeException("Email đã tồn tại");
            }
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setUsername(user.getUsername());
            newUser.setRole(roleRepository.findByRoleName("User"));
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByEmail(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy dữ liệu tài khoản");
        }
        return user;
    }

    @Override
    public void userUpdate(UserDTO userDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        user.setUsername(userDto.getUsername());

        userRepository.save(user);
    }

    @Override
    public boolean checkExistPassword(String password, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public void changePassword(String password, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDTO resultUser = new UserDTO();
            resultUser.setId(user.getId());
            resultUser.setUsername(user.getUsername());
            resultUser.setEmail(user.getEmail());
            resultUser.setRole(user.getRole().getRoleName());
            resultUser.setCreatedAt(user.getCreatedAt());
            userDtos.add(resultUser);
        }
        return userDtos;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByEmail(username);
    }

}
