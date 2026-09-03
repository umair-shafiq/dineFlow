package com.dev.dineFlow.service;

import com.dev.dineFlow.dto.request.ReservationRequestDto;
import com.dev.dineFlow.dto.request.ReservationUpdateRequestDto;
import com.dev.dineFlow.dto.response.ReservationResponseDto;
import com.dev.dineFlow.entity.Reservation;
import com.dev.dineFlow.entity.RestaurantTable;
import com.dev.dineFlow.entity.enums.ReservationStatusEnums;
import com.dev.dineFlow.exception.ResourceNotFoundException;
import com.dev.dineFlow.helper.ReservationMapper;
import com.dev.dineFlow.repository.ReservationRepository;
import com.dev.dineFlow.repository.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService
{
    private static final int RESERVATION_DURATION_HOURS = 2;

    private final ReservationRepository reservationRepository;

    private final RestaurantTableRepository restaurantTableRepository;

    private final ReservationMapper reservationMapper;

    public ReservationResponseDto createReservation(ReservationRequestDto requestDto)
    {
        RestaurantTable restaurantTable = restaurantTableRepository.findById(requestDto.getRestaurantTableId()).orElseThrow(() -> new ResourceNotFoundException("Table Not Found."));

        LocalDateTime requestedTime = requestDto.getReservationDateTime();
        LocalDateTime windowStart = requestedTime.minusHours(RESERVATION_DURATION_HOURS);
        LocalDateTime windowEnd = requestedTime.plusHours(RESERVATION_DURATION_HOURS);

        List<Reservation> conflicts = reservationRepository.findConflictingReservations(
                restaurantTable.getRestaurantTableId(), windowStart, windowEnd
        );

        if (!conflicts.isEmpty())
        {
            throw new IllegalArgumentException("This table is already reserved around the requested time.");
        }

        Reservation reservation = new Reservation();

        reservation.setRestaurantTable(restaurantTable);
        reservation.setCustomerName(requestDto.getCustomerName());
        reservation.setCustomerPhone(requestDto.getCustomerPhone());
        reservation.setNumberOfGuests(requestDto.getNumberOfGuests());
        reservation.setReservationDateTime(requestedTime);

        return reservationMapper.toResponse(reservationRepository.save(reservation));
    }

    public List<ReservationResponseDto> getAllReservations()
    {
        return reservationRepository.findAll().stream().map(reservationMapper::toResponse).toList();
    }

    public ReservationResponseDto getReservationById(Long id)
    {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found."));
        return reservationMapper.toResponse(reservation);
    }

    public ReservationResponseDto toggleReservationStatus(Long id, ReservationStatusEnums reservationStatus)
    {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found."));
        reservation.setStatus(reservationStatus);
        return reservationMapper.toResponse(reservationRepository.save(reservation));
    }

    public ReservationResponseDto updateReservation(Long id, ReservationUpdateRequestDto requestDto)
    {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found."));

        RestaurantTable tableToCheck = reservation.getRestaurantTable();
        if (requestDto.getRestaurantTableId() != null)
        {
            tableToCheck = restaurantTableRepository.findById(requestDto.getRestaurantTableId())
                    .orElseThrow(() -> new ResourceNotFoundException("Table not found."));
        }

        LocalDateTime timeToCheck = requestDto.getReservationDateTime() != null
                ? requestDto.getReservationDateTime()
                : reservation.getReservationDateTime();

        boolean tableChanged = !tableToCheck.getRestaurantTableId().equals(reservation.getRestaurantTable().getRestaurantTableId());
        boolean timeChanged = !timeToCheck.equals(reservation.getReservationDateTime());

        if (tableChanged || timeChanged)
        {
            LocalDateTime windowStart = timeToCheck.minusHours(RESERVATION_DURATION_HOURS);
            LocalDateTime windowEnd = timeToCheck.plusHours(RESERVATION_DURATION_HOURS);

            List<Reservation> conflicts = reservationRepository.findConflictingReservationsExcludingSelf(
                    tableToCheck.getRestaurantTableId(), id, windowStart, windowEnd
            );

            if (!conflicts.isEmpty())
            {
                throw new IllegalArgumentException("This table is already reserved around the requested time.");
            }
        }

        reservation.setRestaurantTable(tableToCheck);
        reservation.setReservationDateTime(timeToCheck);

        if (requestDto.getCustomerName() != null) reservation.setCustomerName(requestDto.getCustomerName());
        if (requestDto.getCustomerPhone() != null) reservation.setCustomerPhone(requestDto.getCustomerPhone());
        if (requestDto.getNumberOfGuests() != null) reservation.setNumberOfGuests(requestDto.getNumberOfGuests());

        return reservationMapper.toResponse(reservationRepository.save(reservation));
    }

    public void deleteReservation(Long id)
    {
        if (reservationRepository.existsById(id))
        {
            reservationRepository.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException("Reservation not found with id: " + id);
        }
    }
}
