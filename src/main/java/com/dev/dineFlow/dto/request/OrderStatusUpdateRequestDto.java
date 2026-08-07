package com.dev.dineFlow.dto.request;

import com.dev.dineFlow.entity.enums.OrderStatusEnums;
import lombok.Data;

@Data
public class OrderStatusUpdateRequestDto
{
    private OrderStatusEnums orderStatus;
}
