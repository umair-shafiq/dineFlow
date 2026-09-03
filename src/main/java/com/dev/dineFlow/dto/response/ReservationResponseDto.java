package com.dev.dineFlow.dto.response;

import com.dev.dineFlow.entity.RestaurantTable;
import com.dev.dineFlow.entity.enums.ReservationStatusEnums;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationResponseDto
{
    private Long reservationId;

    private RestaurantTable restaurantTable;

    private String customerName;

    private String customerPhone;

    private int numberOfGuests;

    private LocalDateTime reservationDateTime;

    private ReservationStatusEnums status;

    private LocalDateTime createdAt;
}
