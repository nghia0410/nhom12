package com.restaurantmanagement.nhom12.service;

import com.restaurantmanagement.nhom12.entity.Food;
import com.restaurantmanagement.nhom12.entity.Restaurant;
import com.restaurantmanagement.nhom12.repository.IFoodRepository;
import com.restaurantmanagement.nhom12.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private IFoodRepository foodRepository;

    public Restaurant saveRestaurant(Restaurant restaurant) {
        restaurant.getMenu().forEach(food -> food.setRestaurant(restaurant));
        return restaurantRepository.save(restaurant);
    }

    public List<Food> findMenuByRestaurant(Restaurant restaurant) {
        return foodRepository.findByRestaurant(restaurant);
    }
}
