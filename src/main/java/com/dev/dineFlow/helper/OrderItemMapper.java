package com.dev.dineFlow.helper;

import com.dev.dineFlow.dto.response.OrderItemResponseDto;
import com.dev.dineFlow.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderItemMapper
{
    private final MenuItemMapper menuItemMapper;

    public OrderItemResponseDto toResponse(OrderItem orderItem)
    {
        OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto();
        orderItemResponseDto.setOrderItemId(orderItem.getOrderItemId());
        orderItemResponseDto.setMenuItem(menuItemMapper.toResponse(orderItem.getMenuItem()));
        orderItemResponseDto.setQuantity(orderItem.getQuantity());
        orderItemResponseDto.setUnitPrice(orderItem.getUnitPrice());
        orderItemResponseDto.setSubtotal(orderItem.getSubtotal());
        return orderItemResponseDto;
    }
}



