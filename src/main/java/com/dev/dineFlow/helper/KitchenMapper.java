package com.dev.dineFlow.helper;

import com.dev.dineFlow.dto.response.KitchenOrderItemResponseDto;
import com.dev.dineFlow.dto.response.KitchenOrderResponseDto;
import com.dev.dineFlow.entity.Order;
import com.dev.dineFlow.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class KitchenMapper
{
    public KitchenOrderResponseDto toResponse(Order order)
    {
        KitchenOrderResponseDto responseDto = new KitchenOrderResponseDto();
        responseDto.setOrderId(order.getOrderId());
        responseDto.setOrderNumber(order.getOrderNumber());
        responseDto.setOrderType(order.getOrderType());
        responseDto.setTableNumber(order.getRestaurantTable().getTableNumber());
        responseDto.setCreatedAt(order.getCreatedAt());
        responseDto.setItems(order.getOrderItems().stream().map(this::toResponseKitchenOrderItem).toList());
        return responseDto;
    }

    public KitchenOrderItemResponseDto toResponseKitchenOrderItem(OrderItem orderItem)
    {
        KitchenOrderItemResponseDto responseDto = new KitchenOrderItemResponseDto();
        responseDto.setOrderItemId(orderItem.getOrderItemId());
        responseDto.setMenuItemName(orderItem.getMenuItem().getName());
        responseDto.setQuantity(orderItem.getQuantity());
        responseDto.setItemStatus(orderItem.getItemStatus());
        return responseDto;
    }
}
