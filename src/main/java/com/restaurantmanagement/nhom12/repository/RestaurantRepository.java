package com.restaurantmanagement.nhom12.repository;

import com.restaurantmanagement.nhom12.dto.RestaurantDTO;
import com.restaurantmanagement.nhom12.entity.Food;
import com.restaurantmanagement.nhom12.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByName(RestaurantDTO restaurantDTO);
}
