package com.restaurantmanagement.nhom12.service;

import com.restaurantmanagement.nhom12.entity.Food;
import com.restaurantmanagement.nhom12.repository.IFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    private IFoodRepository foodRepository;

    public List<Food> getAllFoods() {
        // Call the findAll() method on the injected IFoodRepository instance
        return foodRepository.findAll();
    }

    public Food saveFood(Food food) {
        // Call the save() method on the injected IFoodRepository instance
        return foodRepository.save(food);
    }
}