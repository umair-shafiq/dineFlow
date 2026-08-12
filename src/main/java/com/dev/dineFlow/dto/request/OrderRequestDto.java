package com.dev.dineFlow.dto.request;

import com.dev.dineFlow.entity.enums.OrderTypeEnums;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto
{
    private Long restaurantTableId;

    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemRequestDto> menuItems;

    @NotNull
    private OrderTypeEnums orderType;
}
