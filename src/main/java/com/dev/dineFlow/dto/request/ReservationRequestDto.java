package com.dev.dineFlow.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationRequestDto
{
    @NotNull
    private Long restaurantTableId;

    @NotBlank
    private String customerName;

    @NotBlank
    private String customerPhone;

    @NotNull
    @Min(1)
    private Integer numberOfGuests;

    @NotNull
    @Future(message = "Reservation must be for a future date/time")
    private LocalDateTime reservationDateTime;
}
