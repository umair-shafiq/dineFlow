package com.dev.dineFlow.controller;

import com.dev.dineFlow.dto.request.RestaurantTableRequestDto;
import com.dev.dineFlow.dto.request.RestaurantTableUpdateRequestDto;
import com.dev.dineFlow.dto.response.RestaurantTableResponseDto;
import com.dev.dineFlow.service.RestaurantTableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class RestaurantTableController
{
    private final RestaurantTableService restaurantTableService;

    @PostMapping
    public ResponseEntity<RestaurantTableResponseDto> createRestaurantTable(@Valid @RequestBody RestaurantTableRequestDto requestDto)
    {
        return new ResponseEntity<>(restaurantTableService.createRestaurantTable(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantTableResponseDto>> getAllRestaurantTables()
    {
        return new ResponseEntity<>(restaurantTableService.getAllRestaurantTables(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTableResponseDto> getRestaurantTableById(@PathVariable Long id)
    {
        return new ResponseEntity<>(restaurantTableService.getRestaurantTableById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantTableResponseDto> updateRestaurantTable(@PathVariable Long id, @Valid @RequestBody RestaurantTableUpdateRequestDto requestDto)
    {
        return new ResponseEntity<>(restaurantTableService.updateRestaurantTable(id, requestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurantTable(@PathVariable Long id)
    {
        restaurantTableService.deleteRestaurantTable(id);
        return ResponseEntity.noContent().build();
    }
}
