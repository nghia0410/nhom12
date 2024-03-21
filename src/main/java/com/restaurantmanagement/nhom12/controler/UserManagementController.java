package com.restaurantmanagement.nhom12.controler;

import com.restaurantmanagement.nhom12.dto.UserDTO;
import com.restaurantmanagement.nhom12.entity.User;
import com.restaurantmanagement.nhom12.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/user")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserService userService;
    @GetMapping(value = "")
    public String home(Model model) {
        List<UserDTO> userList = userService.findAll();
        model.addAttribute("users", userList);
        return "admin/user";

    }
}