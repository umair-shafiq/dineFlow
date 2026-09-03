package com.dev.dineFlow.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationUpdateRequestDto
{
    private Long restaurantTableId;

    private String customerName;

    private String customerPhone;

    private Integer numberOfGuests;

    private LocalDateTime reservationDateTime;
}
