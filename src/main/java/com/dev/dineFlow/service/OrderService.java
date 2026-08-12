package com.dev.dineFlow.service;

import com.dev.dineFlow.config.TaxConfig;
import com.dev.dineFlow.dto.request.OrderItemRequestDto;
import com.dev.dineFlow.dto.request.OrderRequestDto;
import com.dev.dineFlow.dto.response.OrderResponseDto;
import com.dev.dineFlow.entity.MenuItem;
import com.dev.dineFlow.entity.Order;
import com.dev.dineFlow.entity.OrderItem;
import com.dev.dineFlow.entity.RestaurantTable;
import com.dev.dineFlow.entity.enums.OrderStatusEnums;
import com.dev.dineFlow.entity.enums.OrderTypeEnums;
import com.dev.dineFlow.exception.ResourceNotFoundException;
import com.dev.dineFlow.helper.OrderMapper;
import com.dev.dineFlow.repository.MenuItemRepository;
import com.dev.dineFlow.repository.OrderRepository;
import com.dev.dineFlow.repository.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService
{
    private final OrderRepository orderRepository;

    private final RestaurantTableRepository restaurantTableRepository;

    private final OrderMapper orderMapper;

    private final MenuItemRepository menuItemRepository;

    private final TaxConfig taxConfig;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto)
    {
        RestaurantTable restaurantTable = null;

        if (orderRequestDto.getOrderType() == OrderTypeEnums.DINE_IN)
        {
            if (orderRequestDto.getRestaurantTableId() == null)
            {
                throw new IllegalArgumentException("Table is required for dine-in orders");
            }

            restaurantTable = restaurantTableRepository.findById(orderRequestDto.getRestaurantTableId()).orElseThrow(() -> new ResourceNotFoundException("Table Not Found."));
        } else if (orderRequestDto.getOrderType() == OrderTypeEnums.TAKEAWAY && orderRequestDto.getRestaurantTableId() != null)
        {
            throw new IllegalArgumentException("Takeaway orders should not have a table assigned");
        }

        Order order = new Order();
        order.setRestaurantTable(restaurantTable);
        order.setOrderType(orderRequestDto.getOrderType());
        order.setOrderNumber(generateOrderNumber());

        double subtotal = 0;

        for (OrderItemRequestDto orderItemRequestDto : orderRequestDto.getMenuItems())
        {
            MenuItem menuItem = menuItemRepository.findById(orderItemRequestDto.getMenuItemId()).orElseThrow(() -> new ResourceNotFoundException("MenuItem not found"));

            double itemSubtotal = menuItem.getPrice() * orderItemRequestDto.getQuantity();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(orderItemRequestDto.getQuantity());
            orderItem.setUnitPrice(menuItem.getPrice());
            orderItem.setSubtotal(itemSubtotal);

            order.getOrderItems().add(orderItem);
            subtotal += itemSubtotal;
        }

        double taxRate = taxConfig.getPercentage() / 100.0;
        double taxAmount = subtotal * taxRate;

        order.setSubtotal(subtotal);
        order.setTaxAmount(taxAmount);
        order.setTotalAmount(subtotal + taxAmount);

        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAllOrders()
    {
        return orderRepository.findAll().stream().map(orderMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderByOrderNumber(String orderNumber)
    {
        Order order = orderRepository.findByOrderNumber(orderNumber).orElseThrow(() -> new ResourceNotFoundException("Record not found against this Order Number: " + orderNumber));
        return orderMapper.toResponse(order);
    }

    public OrderResponseDto updateOrderStatus(Long id, OrderStatusEnums newStatus)
    {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        order.setOrderStatus(newStatus);
        Order updated = orderRepository.save(order);
        return orderMapper.toResponse(updated);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getActiveOrders()
    {
        List<OrderStatusEnums> activeStatuses = List.of(OrderStatusEnums.PLACED, OrderStatusEnums.IN_PROGRESS, OrderStatusEnums.SERVED);
        return orderRepository.findByOrderStatusIn(activeStatuses).stream().map(orderMapper::toResponse).toList();
    }

    private String generateOrderNumber()
    {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomPart = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return "ORD-" + datePart + "-" + randomPart;
    }
}
