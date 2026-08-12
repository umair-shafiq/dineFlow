package com.dev.dineFlow.dto.response;

import com.dev.dineFlow.entity.enums.OrderStatusEnums;
import com.dev.dineFlow.entity.enums.OrderTypeEnums;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto
{
    private Long orderId;

    private String orderNumber;

    private RestaurantTableResponseDto restaurantTable;

    private OrderStatusEnums orderStatus;

    private OrderTypeEnums orderType;

    private double subtotal;

    private double taxAmount;

    private double totalAmount;

    private List<OrderItemResponseDto> orderItems;

    private LocalDateTime createdAt;
}
