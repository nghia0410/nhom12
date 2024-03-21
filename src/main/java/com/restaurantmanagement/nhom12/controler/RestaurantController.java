package com.restaurantmanagement.nhom12.controler;

import com.restaurantmanagement.nhom12.dto.RestaurantDTO;
import com.restaurantmanagement.nhom12.entity.Restaurant;
import com.restaurantmanagement.nhom12.repository.IFoodRepository;
import com.restaurantmanagement.nhom12.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class RestaurantController {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private IFoodRepository foodRepository;

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantDetails(@PathVariable Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).get();
        RestaurantDTO restaurantDTO = convertRestaurantToDTO(restaurant);
        return ResponseEntity.ok(restaurantDTO);
    }

    private RestaurantDTO convertRestaurantToDTO(Restaurant restaurant) {
        // ... Logic chuyển đổi sang RestaurantDTO (giống như đoạn code bạn cung cấp)
        return null;
    }
}
