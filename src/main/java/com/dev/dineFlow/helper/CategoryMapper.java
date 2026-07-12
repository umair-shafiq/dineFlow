package com.dev.dineFlow.helper;

import com.dev.dineFlow.dto.request.CategoryRequestDto;
import com.dev.dineFlow.dto.response.CategoryResponseDto;
import com.dev.dineFlow.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper
{

    public CategoryResponseDto toResponse(Category category)
    {
        return new CategoryResponseDto(category.getId(), category.getName());
    }

    public Category toEntity(CategoryRequestDto request)
    {
        Category category = new Category();
        category.setName(request.getName());
        return category;
    }
}
