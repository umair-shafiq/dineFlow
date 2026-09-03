package com.dev.dineFlow.service;

import com.dev.dineFlow.dto.request.RestaurantTableRequestDto;
import com.dev.dineFlow.dto.request.RestaurantTableUpdateRequestDto;
import com.dev.dineFlow.dto.response.RestaurantTableResponseDto;
import com.dev.dineFlow.entity.RestaurantTable;
import com.dev.dineFlow.exception.DuplicateResourceException;
import com.dev.dineFlow.exception.ResourceNotFoundException;
import com.dev.dineFlow.helper.RestaurantTableMapper;
import com.dev.dineFlow.repository.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantTableService
{
    private final RestaurantTableRepository restaurantTableRepository;

    private final RestaurantTableMapper tableMapper;

    public RestaurantTableResponseDto createRestaurantTable(RestaurantTableRequestDto requestDto)
    {
        boolean tableAlreadyExist = restaurantTableRepository.existsByTableNumberIgnoreCase(requestDto.getTableNumber().trim());
        if (tableAlreadyExist)
        {
            throw new DuplicateResourceException("A table with this number already exists.");
        }

        RestaurantTable restaurantTable = new RestaurantTable();
        restaurantTable.setTableNumber(requestDto.getTableNumber());
        restaurantTable.setCapacity(requestDto.getCapacity());
        restaurantTable.setTableStatus(requestDto.getTableStatus());

        return tableMapper.toResponse(restaurantTableRepository.save(restaurantTable));
    }

    public List<RestaurantTableResponseDto> getAllRestaurantTables()
    {
        return restaurantTableRepository.findAll().stream().map(tableMapper::toResponse).toList();
    }

    public RestaurantTableResponseDto getRestaurantTableById(Long id)
    {
        RestaurantTable restaurantTable = restaurantTableRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant Table not found."));
        return tableMapper.toResponse(restaurantTable);
    }

    public RestaurantTableResponseDto updateRestaurantTable(Long id, RestaurantTableUpdateRequestDto requestDto)
    {
        RestaurantTable restaurantTable = restaurantTableRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Table Not found."));

        if (requestDto.getTableNumber() != null)
        {
            if (restaurantTableRepository.existsByTableNumberIgnoreCaseAndRestaurantTableIdNot(requestDto.getTableNumber().trim(), id))
            {
                throw new DuplicateResourceException("Another table already uses this number.");
            }
            restaurantTable.setTableNumber(requestDto.getTableNumber());
        }

        if (requestDto.getCapacity() != null)
        {
            restaurantTable.setCapacity(requestDto.getCapacity());
        }


        if (requestDto.getTableStatus() != null)
        {
            restaurantTable.setTableStatus(requestDto.getTableStatus());
        }

        return tableMapper.toResponse(restaurantTableRepository.save(restaurantTable));
    }

    public void deleteRestaurantTable(Long id)
    {
        if (restaurantTableRepository.existsById(id))
        {
            restaurantTableRepository.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException("Table not found with id: " + id);
        }
    }
}
