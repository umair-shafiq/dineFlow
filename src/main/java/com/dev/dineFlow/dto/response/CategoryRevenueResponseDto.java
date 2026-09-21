package com.dev.dineFlow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryRevenueResponseDto
{
    private String categoryName;
    private double totalRevenue;
}
