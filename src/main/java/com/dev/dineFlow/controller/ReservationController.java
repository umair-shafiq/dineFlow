package com.dev.dineFlow.controller;

import com.dev.dineFlow.dto.request.ReservationRequestDto;
import com.dev.dineFlow.dto.request.ReservationUpdateRequestDto;
import com.dev.dineFlow.dto.response.ReservationResponseDto;
import com.dev.dineFlow.entity.enums.ReservationStatusEnums;
import com.dev.dineFlow.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController
{
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody @Valid ReservationRequestDto requestDto)
    {
        return new ResponseEntity<>(reservationService.createReservation(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations()
    {
        return new ResponseEntity<>(reservationService.getAllReservations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable Long id)
    {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ReservationResponseDto> toggleReservationStatus(@PathVariable Long id, @RequestParam ReservationStatusEnums reservationStatus)
    {
        return ResponseEntity.ok(reservationService.toggleReservationStatus(id, reservationStatus));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> updateReservation(@PathVariable Long id, @RequestBody @Valid ReservationUpdateRequestDto requestDto)
    {
        return ResponseEntity.ok(reservationService.updateReservation(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id)
    {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
