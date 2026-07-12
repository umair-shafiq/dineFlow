package com.dev.dineFlow.service;

import com.dev.dineFlow.dto.request.CategoryRequestDto;
import com.dev.dineFlow.dto.response.CategoryResponseDto;
import com.dev.dineFlow.entity.Category;
import com.dev.dineFlow.exception.DuplicateResourceException;
import com.dev.dineFlow.exception.ResourceNotFoundException;
import com.dev.dineFlow.helper.CategoryMapper;
import com.dev.dineFlow.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto)
    {
        Category category = categoryMapper.toEntity(categoryRequestDto);
        if (categoryRepository.existsByNameIgnoreCase(categoryRequestDto.getName().trim()))
        {
            throw new DuplicateResourceException("Category already exists with name");
        }
        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    public List<CategoryResponseDto> getAllCategories()
    {
        return categoryRepository.findAll().stream().map(categoryMapper::toResponse).toList();
    }

    public CategoryResponseDto getCategoryById(Long id)
    {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return categoryMapper.toResponse(category);
    }

    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto updatedCategory)
    {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        category.setName(updatedCategory.getName());
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    public void deleteCategory(Long id)
    {
        if (categoryRepository.existsById(id))
        {
            categoryRepository.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
    }
}
