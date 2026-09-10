package com.dev.dineFlow.controller;

import com.dev.dineFlow.dto.response.KitchenOrderItemResponseDto;
import com.dev.dineFlow.dto.response.KitchenOrderResponseDto;
import com.dev.dineFlow.entity.enums.OrderItemStatusEnums;
import com.dev.dineFlow.service.KitchenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kitchen")
@RequiredArgsConstructor
public class KitchenController
{
    private final KitchenService kitchenService;

    @GetMapping("/orders")
    public ResponseEntity<List<KitchenOrderResponseDto>> getKitchenOrders()
    {
        return ResponseEntity.ok(kitchenService.getKitchenOrders());
    }

    @PatchMapping("/order-items/{id}/status")
    public ResponseEntity<KitchenOrderItemResponseDto> updateOrderItemStatus(
            @PathVariable Long id,
            @RequestParam OrderItemStatusEnums status)
    {
        return ResponseEntity.ok(kitchenService.updateOrderItemStatus(id, status));
    }
}
