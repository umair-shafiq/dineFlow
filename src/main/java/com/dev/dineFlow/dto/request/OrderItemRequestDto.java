package com.dev.dineFlow.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemRequestDto
{
    @NotNull(message = "Menu item ID is required")
    private Long menuItemId;

    @Positive(message = "Quantity must be greater than 0")
    private int quantity;
}
