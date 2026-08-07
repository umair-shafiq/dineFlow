package com.dev.dineFlow.controller;

import com.dev.dineFlow.dto.request.OrderRequestDto;
import com.dev.dineFlow.dto.request.OrderStatusUpdateRequestDto;
import com.dev.dineFlow.dto.response.OrderResponseDto;
import com.dev.dineFlow.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController
{
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto)
    {
        return new ResponseEntity<>(orderService.createOrder(orderRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrder()
    {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderResponseDto> getOrderByOrderNumber(@PathVariable String orderNumber)
    {
        return new ResponseEntity<>(orderService.getOrderByOrderNumber(orderNumber), HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody OrderStatusUpdateRequestDto status)
    {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status.getOrderStatus()));
    }

    @GetMapping("/active")
    public ResponseEntity<List<OrderResponseDto>> getActiveOrders()
    {
        return ResponseEntity.ok(orderService.getActiveOrders());
    }
}
