package com.restaurantmanagement.nhom12.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientControler {
    @GetMapping(value = "/")
    public String home() {
        return "index";
    }
}
