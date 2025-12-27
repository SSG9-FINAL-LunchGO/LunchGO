package com.example.LunchGo.restaurant.repository;

import com.example.LunchGo.restaurant.domain.MenuCategory;
import com.example.LunchGo.restaurant.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByRestaurantIdAndIsDeletedFalseOrderByMenuIdAsc(Long restaurantId);

    @Modifying
    @Query("""
            UPDATE Menu m
               SET m.name = :name,
                   m.category = :category,
                   m.description = :description,
                   m.price = :price
             WHERE m.menuId = :menuId
               AND m.restaurantId = :restaurantId
               AND m.isDeleted = false
            """)
    int updateMenu(@Param("restaurantId") Long restaurantId,
                   @Param("menuId") Long menuId,
                   @Param("name") String name,
                   @Param("category") MenuCategory category,
                   @Param("description") String description,
                   @Param("price") Integer price);

    @Modifying
    @Query("""
            UPDATE Menu m
               SET m.isDeleted = true
             WHERE m.menuId = :menuId
               AND m.restaurantId = :restaurantId
               AND m.isDeleted = false
            """)
    int softDeleteMenu(@Param("restaurantId") Long restaurantId,
                       @Param("menuId") Long menuId);
}
