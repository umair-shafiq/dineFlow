package com.dev.dineFlow.dto.response;

import com.dev.dineFlow.entity.enums.OrderItemStatusEnums;
import lombok.Data;

@Data
public class KitchenOrderItemResponseDto
{
    private Long orderItemId;

    private String menuItemName;

    private int quantity;

    private OrderItemStatusEnums itemStatus;
}
