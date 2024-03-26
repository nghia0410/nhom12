package com.restaurantmanagement.nhom12.controler;

import com.restaurantmanagement.nhom12.dto.RestaurantDTO;
import com.restaurantmanagement.nhom12.entity.Restaurant;
import com.restaurantmanagement.nhom12.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/admin/restaurants")
public class AdminRestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping(value = {"/list", ""})
    public String getRestaurantList(Model model) {
        List<Restaurant> restaurantList = restaurantService.findAll();
        model.addAttribute("restaurants", restaurantList);
        return "/admin/restaurant/restaurantList";
    }

    @GetMapping(value = "/create")
    public String showFormRestaurantCreate(Model model) {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        model.addAttribute("restaurantDTO", restaurantDTO);
        return "/admin/restaurant/restaurantCreate";
    }

    @PostMapping(value = "/create")
    public String createRestaurant(@Valid RestaurantDTO restaurantDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/restaurant/restaurantCreate";
        }

        restaurantService.saveRestaurant(restaurantDTO);
        return "redirect:/admin/restaurants/list";
    }

    @GetMapping(value = "/update/{id}")
    public String showEditRestaurantForm(@PathVariable("id") Long restaurantId, Model model) {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        RestaurantDTO restaurantDTO = new RestaurantDTO();
        BeanUtils.copyProperties(restaurant, restaurantDTO);

        model.addAttribute("updatedRestaurantDto", restaurantDTO);

        return "admin/restaurant/restaurantEdit";
    }

    @PostMapping("/update/{id}")
    public String updateRestaurant(@PathVariable("id") Long restaurantId,
                                   RestaurantDTO restaurantDTO) {
        // Gọi service để cập nhật thông tin nhà hàng
        restaurantService.updateRestaurant(restaurantId, restaurantDTO);

        return "redirect:/admin/restaurants/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteRestaurant(@PathVariable("id") Long restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return "redirect:/admin/restaurants/list";
    }
}
