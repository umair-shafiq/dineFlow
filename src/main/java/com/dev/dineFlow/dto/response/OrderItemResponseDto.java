package com.dev.dineFlow.dto.response;

import lombok.Data;

@Data
public class OrderItemResponseDto
{
    private Long orderItemId;

    private MenuItemResponseDto menuItem;

    private int quantity;

    private double unitPrice;

    private double subtotal;
}
