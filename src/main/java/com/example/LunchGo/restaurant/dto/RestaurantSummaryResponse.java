package com.example.LunchGo.restaurant.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantSummaryResponse {

    private Long id;
    private String name;
    private String image;
    private String roadAddress;
    private String detailAddress;
    private Double rating;
    private Long reviews;
    private String category;
    private Integer price;
    private Double latitude;
    private Double longitude;

}
