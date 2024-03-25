package com.restaurantmanagement.nhom12.controler;

import com.restaurantmanagement.nhom12.dto.RestaurantDTO;
import com.restaurantmanagement.nhom12.entity.Restaurant;
import com.restaurantmanagement.nhom12.repository.IFoodRepository;
import com.restaurantmanagement.nhom12.repository.RestaurantRepository;
import com.restaurantmanagement.nhom12.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping(value = "")
    public String getRestaurantDetails(Model model) {
        List<Restaurant> restaurantList = restaurantService.findAll();
        model.addAttribute("restaurantList", restaurantList);
        return "admin/restaurants";
    }

    @PostMapping(value = "/admin")
    public String createRestaurant(@Valid RestaurantDTO restaurantDTO) {
        // Gọi phương thức saveRestaurant của RestaurantService để lưu dữ liệu vào cơ sở dữ liệu
        restaurantService.saveRestaurant(restaurantDTO);
        return "redirect:/admin";
    }
}
