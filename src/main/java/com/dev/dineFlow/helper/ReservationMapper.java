package com.dev.dineFlow.helper;

import com.dev.dineFlow.dto.response.ReservationResponseDto;
import com.dev.dineFlow.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper
{
    public ReservationResponseDto toResponse(Reservation reservation)
    {
        ReservationResponseDto responseDto = new ReservationResponseDto();
        responseDto.setReservationId(reservation.getReservationId());
        responseDto.setRestaurantTable(reservation.getRestaurantTable());
        responseDto.setCustomerName(reservation.getCustomerName());
        responseDto.setCustomerPhone(reservation.getCustomerPhone());
        responseDto.setNumberOfGuests(reservation.getNumberOfGuests());
        responseDto.setReservationDateTime(reservation.getReservationDateTime());
        responseDto.setStatus(reservation.getStatus());
        responseDto.setCreatedAt(reservation.getCreatedAt());
        return responseDto;
    }
}
