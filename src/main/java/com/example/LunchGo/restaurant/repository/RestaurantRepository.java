package com.example.LunchGo.restaurant.repository;

import com.example.LunchGo.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.restaurantImages WHERE r.restaurantId = :restaurantId")
    Optional<Restaurant> findByIdWithImages(@Param("restaurantId") Long restaurantId);

    @Query(value = "SELECT tag_id FROM restaurant_tag_maps WHERE restaurant_id = :restaurantId", nativeQuery = true)
    List<Long> findTagIdsByRestaurantId(@Param("restaurantId") Long restaurantId);
}
