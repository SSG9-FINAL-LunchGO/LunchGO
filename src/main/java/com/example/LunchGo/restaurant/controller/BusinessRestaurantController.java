package com.example.LunchGo.restaurant.controller;

import com.example.LunchGo.restaurant.dto.RestaurantDetailResponse;
import com.example.LunchGo.restaurant.service.BusinessRestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/business/restaurants")
@RequiredArgsConstructor
public class BusinessRestaurantController {

    private final BusinessRestaurantService businessRestaurantService;

    /**
     * 사업자용 식당 상세 정보 조회 API
     * @param id 식당 ID
     * @return RestaurantDetailResponse DTO를 포함하는 ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDetailResponse> getRestaurantDetail(@PathVariable("id") Long id) {
        RestaurantDetailResponse response = businessRestaurantService.getRestaurantDetail(id);
        return ResponseEntity.ok(response);
    }
}