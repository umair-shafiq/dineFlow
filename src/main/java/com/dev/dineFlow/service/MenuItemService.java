package com.dev.dineFlow.service;

import com.dev.dineFlow.dto.request.MenuItemRequestDto;
import com.dev.dineFlow.dto.response.MenuItemResponseDto;
import com.dev.dineFlow.entity.Category;
import com.dev.dineFlow.entity.MenuItem;
import com.dev.dineFlow.entity.enums.AvailabilityStatusEnums;
import com.dev.dineFlow.exception.DuplicateResourceException;
import com.dev.dineFlow.exception.ResourceNotFoundException;
import com.dev.dineFlow.helper.MenuItemMapper;
import com.dev.dineFlow.repository.CategoryRepository;
import com.dev.dineFlow.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService
{
    private final MenuItemRepository menuItemRepository;

    private final CategoryRepository categoryRepository;

    private final MenuItemMapper menuItemMapper;

    public MenuItemResponseDto createMenuItem(MenuItemRequestDto menuItemRequestDto)
    {
        Category category = categoryRepository.findById(menuItemRequestDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        MenuItem menuItem = menuItemMapper.toEntity(menuItemRequestDto);

        if (menuItemRepository.existsByNameIgnoreCase(menuItemRequestDto.getName().trim()))
        {
            throw new DuplicateResourceException("Menu Item already exists with this name");
        }
        menuItem.setCategory(category);
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return menuItemMapper.toResponse(savedMenuItem);
    }

    public MenuItemResponseDto updateMenuItem(MenuItemRequestDto itemRequestDto, Long id)
    {
        MenuItem existingMenuItems = menuItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));
        if (menuItemRepository.existsByNameIgnoreCaseAndMenuItemIdNot(itemRequestDto.getName().trim(), id))
        {
            throw new DuplicateResourceException("Another menu item already exists with this name");
        }

        Category category = categoryRepository.findById(itemRequestDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + itemRequestDto.getCategoryId()));
        existingMenuItems.setName(itemRequestDto.getName());
        existingMenuItems.setDescription(itemRequestDto.getDescription());
        existingMenuItems.setPrice(itemRequestDto.getPrice());
        existingMenuItems.setImageUrl(itemRequestDto.getImageUrl());
        existingMenuItems.setCategory(category);

        MenuItem menuItem = menuItemRepository.save(existingMenuItems);
        return menuItemMapper.toResponse(menuItem);
    }

    public MenuItemResponseDto updateAvailability(Long id, AvailabilityStatusEnums status)
    {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + id));
        menuItem.setAvailabilityStatus(status);
        MenuItem updated = menuItemRepository.save(menuItem);
        return menuItemMapper.toResponse(updated);
    }

    public List<MenuItemResponseDto> getAllMenuItems()
    {
        return menuItemRepository.findAll().stream().map(menuItemMapper::toResponse).toList();
    }

    public MenuItemResponseDto getMenuItemById(Long id)
    {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Record not found against this id: " + id));
        return menuItemMapper.toResponse(menuItem);
    }

    public void deleteMenuItem(Long id)
    {
        if (menuItemRepository.existsById(id))
        {
            menuItemRepository.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException("Resource Not Found: " + id);
        }
    }
}
