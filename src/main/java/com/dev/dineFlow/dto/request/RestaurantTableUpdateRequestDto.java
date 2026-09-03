package com.dev.dineFlow.dto.request;

import com.dev.dineFlow.entity.enums.TableStatusEnums;
import lombok.Data;

@Data
public class RestaurantTableUpdateRequestDto
{
    private String tableNumber;

    private Integer capacity;

    private TableStatusEnums tableStatus;
}
