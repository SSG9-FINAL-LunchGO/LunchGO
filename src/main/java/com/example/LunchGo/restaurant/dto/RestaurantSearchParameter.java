package com.example.LunchGo.restaurant.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class RestaurantSearchParameter {
    private LocalDate date;
    private LocalTime time;
    private Integer partySize;
    private List<String> menuTypes;
}
