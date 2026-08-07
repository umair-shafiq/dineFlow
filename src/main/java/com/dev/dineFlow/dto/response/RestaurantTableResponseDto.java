package com.dev.dineFlow.dto.response;

import com.dev.dineFlow.entity.enums.TableStatusEnums;
import lombok.Data;

@Data
public class RestaurantTableResponseDto
{
    private Long restaurantTableId;

    private String tableNumber;

    private int capacity;

    private TableStatusEnums tableStatus;
}
