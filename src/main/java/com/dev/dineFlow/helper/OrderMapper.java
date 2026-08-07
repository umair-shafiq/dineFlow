package com.dev.dineFlow.helper;

import com.dev.dineFlow.dto.response.OrderResponseDto;
import com.dev.dineFlow.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper
{
    private final RestaurantTableMapper restaurantTableMapper;

    private final OrderItemMapper orderItemMapper;

    public OrderResponseDto toResponse(Order order)
    {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderNumber(order.getOrderNumber());
        orderResponseDto.setRestaurantTable(restaurantTableMapper.toResponse(order.getRestaurantTable()));
        orderResponseDto.setOrderId(order.getOrderId());
        orderResponseDto.setOrderStatus(order.getOrderStatus());
        orderResponseDto.setSubtotal(order.getSubtotal());
        orderResponseDto.setTaxAmount(order.getTaxAmount());
        orderResponseDto.setTotalAmount(order.getTotalAmount());
        orderResponseDto.setOrderItems(order.getOrderItems().stream().map(orderItemMapper::toResponse).toList());
        orderResponseDto.setCreatedAt(order.getCreatedAt());

        return orderResponseDto;
    }

}
