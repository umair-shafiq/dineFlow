package com.dev.dineFlow.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto
{
    @NotNull
    private Long restaurantTableId;

    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemRequestDto> menuItems;

}
