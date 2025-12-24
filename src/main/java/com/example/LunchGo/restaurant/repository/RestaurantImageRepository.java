package com.example.LunchGo.restaurant.repository;

import com.example.LunchGo.restaurant.entity.RestaurantImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantImageRepository extends JpaRepository<RestaurantImage, Long> {
}
