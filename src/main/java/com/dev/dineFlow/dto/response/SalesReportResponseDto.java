package com.dev.dineFlow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalesReportResponseDto
{
    private long totalOrders;
    private double totalRevenue;
    private double totalTax;
}
