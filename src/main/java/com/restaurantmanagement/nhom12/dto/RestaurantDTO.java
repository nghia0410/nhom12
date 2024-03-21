package com.restaurantmanagement.nhom12.dto;

import com.restaurantmanagement.nhom12.entity.Food;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter @Setter

public class RestaurantDTO {
    private Long id;
    private String name;
    private String address;
    private String description;
    private List<Food> menu;

}
