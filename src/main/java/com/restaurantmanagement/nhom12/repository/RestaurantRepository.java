package com.restaurantmanagement.nhom12.repository;

import com.restaurantmanagement.nhom12.dto.RestaurantDTO;
import com.restaurantmanagement.nhom12.entity.Food;
import com.restaurantmanagement.nhom12.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByRestaurntid(Long restaurantId);
}
