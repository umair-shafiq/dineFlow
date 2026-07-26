package com.dev.dineFlow.helper;

import com.dev.dineFlow.dto.request.MenuItemRequestDto;
import com.dev.dineFlow.dto.response.CategoryResponseDto;
import com.dev.dineFlow.dto.response.MenuItemResponseDto;
import com.dev.dineFlow.entity.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper
{
    public MenuItem toEntity(MenuItemRequestDto menuItemRequestDto)
    {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequestDto.getName());
        menuItem.setDescription(menuItemRequestDto.getDescription());
        menuItem.setPrice(menuItemRequestDto.getPrice());
        menuItem.setImageUrl(menuItemRequestDto.getImageUrl());
        return menuItem;
    }

    public MenuItemResponseDto toResponse(MenuItem menuItem)
    {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(menuItem.getCategory().getId(), menuItem.getCategory().getName());

        return new MenuItemResponseDto(menuItem.getMenuItemId(), menuItem.getName(), menuItem.getDescription(), menuItem.getPrice(), menuItem.getImageUrl(), categoryResponseDto);
    }
}
