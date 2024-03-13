package com.restaurantmanagement.nhom12.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/signin")
public class SignInControler {
    @GetMapping(value = "")
    public String home() {
        return "sign-in";
    }
}
