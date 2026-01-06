package com.example.LunchGo.reservation.mapper.row;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationMenuItemRow {

    // 쿼리 alias가 name이든 menuName이든 둘 다 받아먹게
    private String name;
    private String menuName;

    private Integer quantity;
    private Integer unitPrice;
    private Integer lineAmount;

    // BusinessReservationQueryService가 getMenuName()을 호출해도 null 안 나오게 fallback
    public String getMenuName() {
        return menuName != null ? menuName : name;
    }

    // ReservationPaymentService는 getName()을 쓰니까 반대 fallback도 같이
    public String getName() {
        return name != null ? name : menuName;
    }
}