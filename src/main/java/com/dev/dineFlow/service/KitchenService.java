package com.dev.dineFlow.service;

import com.dev.dineFlow.dto.response.KitchenOrderItemResponseDto;
import com.dev.dineFlow.dto.response.KitchenOrderResponseDto;
import com.dev.dineFlow.entity.OrderItem;
import com.dev.dineFlow.entity.enums.OrderItemStatusEnums;
import com.dev.dineFlow.entity.enums.OrderStatusEnums;
import com.dev.dineFlow.exception.ResourceNotFoundException;
import com.dev.dineFlow.helper.KitchenMapper;
import com.dev.dineFlow.repository.OrderItemRepository;
import com.dev.dineFlow.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KitchenService
{
    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final KitchenMapper kitchenMapper;

    @Transactional(readOnly = true)
    public List<KitchenOrderResponseDto> getKitchenOrders()
    {
        List<OrderStatusEnums> relevantStatuses = List.of(
                OrderStatusEnums.PLACED, OrderStatusEnums.IN_PROGRESS
        );

        return orderRepository.findByOrderStatusIn(relevantStatuses).stream().map(kitchenMapper::toResponse).toList();


    }

    @Transactional
    public KitchenOrderItemResponseDto updateOrderItemStatus(Long id, OrderItemStatusEnums status)
    {
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order Item Not Found"));
        orderItem.setItemStatus(status);
        return kitchenMapper.toResponseKitchenOrderItem(orderItemRepository.save(orderItem));
    }
}
