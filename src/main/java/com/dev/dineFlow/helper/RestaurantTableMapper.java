package com.dev.dineFlow.helper;

import com.dev.dineFlow.dto.response.RestaurantTableResponseDto;
import com.dev.dineFlow.entity.RestaurantTable;
import org.springframework.stereotype.Component;

@Component
public class RestaurantTableMapper
{
    public RestaurantTableResponseDto toResponse(RestaurantTable restaurantTable)
    {
        RestaurantTableResponseDto restaurantTableResponseDto = new RestaurantTableResponseDto();
        restaurantTableResponseDto.setRestaurantTableId(restaurantTable.getRestaurantTableId());
        restaurantTableResponseDto.setTableNumber(restaurantTable.getTableNumber());
        restaurantTableResponseDto.setCapacity(restaurantTable.getCapacity());
        restaurantTableResponseDto.setTableStatus(restaurantTable.getTableStatus());
        return restaurantTableResponseDto;
    }
}