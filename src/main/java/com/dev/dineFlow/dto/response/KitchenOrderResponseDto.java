package com.dev.dineFlow.dto.response;

import com.dev.dineFlow.entity.enums.OrderTypeEnums;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class KitchenOrderResponseDto
{
    private Long orderId;

    private String orderNumber;

    private OrderTypeEnums orderType;

    private String tableNumber;

    private LocalDateTime createdAt;

    private List<KitchenOrderItemResponseDto> items;
}
