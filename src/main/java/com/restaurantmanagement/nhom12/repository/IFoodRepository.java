package com.restaurantmanagement.nhom12.repository;

import com.restaurantmanagement.nhom12.entity.Food;
import com.restaurantmanagement.nhom12.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByRestaurant(Restaurant restaurant);  // Replace with findByRestaurant(Restaurant restaurant)

}
