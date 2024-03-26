package com.restaurantmanagement.nhom12.service;

import com.restaurantmanagement.nhom12.dto.RestaurantDTO;
import com.restaurantmanagement.nhom12.entity.Restaurant;
import com.restaurantmanagement.nhom12.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public void saveRestaurant(RestaurantDTO restaurantDTO) {
        // Tạo mới một đối tượng Restaurant từ dữ liệu DTO
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDTO.getName());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setDescription(restaurantDTO.getDescription());

        // Lưu đối tượng vào cơ sở dữ liệu
        restaurantRepository.save(restaurant);
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public void updateRestaurant(Long restaurantId, RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.findByRestaurntid(restaurantId);
        restaurant.setName(restaurantDTO.getName());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setDescription(restaurantDTO.getDescription());
        restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    public Restaurant findRestaurantById(Long restaurantId) {
        return restaurantRepository.findByRestaurntid(restaurantId);
    }

    // Có thể thêm các phương thức khác để thao tác với Restaurant
}
