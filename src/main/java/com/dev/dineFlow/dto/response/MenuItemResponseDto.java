package com.dev.dineFlow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MenuItemResponseDto
{
    private Long id;

    private String name;

    private String description;

    private double price;

    private String imageUrl;

    private CategoryResponseDto category;
}
