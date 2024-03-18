package com.restaurantmanagement.nhom12.utility;

import com.restaurantmanagement.nhom12.entity.Role;
import com.restaurantmanagement.nhom12.entity.User;
import com.restaurantmanagement.nhom12.repository.IRoleRepository;
import com.restaurantmanagement.nhom12.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDataLoader implements CommandLineRunner {
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.count() == 0) {
            Role roleAdmin = new Role();
            roleAdmin.setRoleName("ADMIN");
            roleRepository.save(roleAdmin);

            Role roleUser = new Role();
            roleUser.setRoleName("USER");
            roleRepository.save(roleUser);
        }
        if(userRepository.count() == 0) {
            User newAdmin = new User();
            newAdmin.setEmail("admin@gmail.com");
            newAdmin.setPassword(passwordEncoder.encode("Admin@123456"));
            newAdmin.setUsername("Admin");
            newAdmin.setRole(roleRepository.findByRoleName("ADMIN"));
            userRepository.save(newAdmin);

            User newUser = new User();
            newUser.setEmail("user@gmail.com");
            newUser.setPassword(passwordEncoder.encode("User@123456"));
            newUser.setUsername("User");
            newUser.setRole(roleRepository.findByRoleName("USER"));
            userRepository.save(newUser);
        }
    }
}
