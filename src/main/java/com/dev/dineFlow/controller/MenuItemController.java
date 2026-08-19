package com.dev.dineFlow.controller;

import com.dev.dineFlow.dto.request.MenuItemRequestDto;
import com.dev.dineFlow.dto.response.MenuItemResponseDto;
import com.dev.dineFlow.entity.enums.AvailabilityStatusEnums;
import com.dev.dineFlow.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController
{
    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemResponseDto> createMenuItem(
            @Valid @RequestBody MenuItemRequestDto menuItemRequestDto)
    {
        return new ResponseEntity<>(menuItemService.createMenuItem(menuItemRequestDto), HttpStatusCode.valueOf(201));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponseDto> updateMenuItem(@PathVariable Long id, @Valid @RequestBody MenuItemRequestDto itemRequestDto)
    {
        return new ResponseEntity<>(menuItemService.updateMenuItem(itemRequestDto, id), HttpStatusCode.valueOf(200));
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<MenuItemResponseDto> updateAvailability(
            @PathVariable Long id,
            @RequestParam(name = "status", defaultValue = "AVAILABLE") AvailabilityStatusEnums status)
    {
        return ResponseEntity.ok(menuItemService.updateAvailability(id, status));
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDto>> getAllMenuItems()
    {
        return new ResponseEntity<>(menuItemService.getAllMenuItems(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponseDto> getMenuItemById(@PathVariable Long id)
    {
        return new ResponseEntity<>(menuItemService.getMenuItemById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id)
    {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }
}