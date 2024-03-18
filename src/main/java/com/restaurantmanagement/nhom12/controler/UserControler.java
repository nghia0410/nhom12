package com.restaurantmanagement.nhom12.controler;

import com.restaurantmanagement.nhom12.dto.SignUpDTO;
import com.restaurantmanagement.nhom12.entity.User;
import com.restaurantmanagement.nhom12.repository.IRoleRepository;
import com.restaurantmanagement.nhom12.repository.IUserRepository;
import com.restaurantmanagement.nhom12.service.implement.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserControler {
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserService userService;
    @GetMapping("/sign-in")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/";
        }
        return "sign-in";
    }
    @GetMapping("/sign-up")
    public String showRegisterUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/sign-in";
        }

        model.addAttribute("user", new SignUpDTO());
        return "sign-up";
    }
    @PostMapping("/sign-up")
    public String registerUserAccount(@ModelAttribute("user") @Valid SignUpDTO signUpDto,
                                      HttpServletRequest request, Errors errors) {
        User newUser = new User();
        newUser.setEmail(signUpDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        newUser.setUsername(signUpDto.getUsername());
        newUser.setRole(roleRepository.findByRoleName("USER"));
        userRepository.save(newUser);
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logOutCallback() {
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}

