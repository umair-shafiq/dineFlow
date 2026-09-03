package com.dev.dineFlow.dto.request;

import com.dev.dineFlow.entity.enums.TableStatusEnums;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestaurantTableRequestDto
{
    @NotBlank
    private String tableNumber;

    @NotNull
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @NotNull
    private TableStatusEnums tableStatus;
}
