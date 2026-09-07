package com.dev.dineFlow.dto.response;

import com.dev.dineFlow.entity.enums.PaymentStatusEnums;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvoiceResponseDto
{
    private Long invoiceId;

    private OrderResponseDto order;

    private String invoiceNumber;

    private double subtotal;

    private double taxAmount;

    private double totalAmount;

    private PaymentStatusEnums paymentStatus;

    private LocalDateTime createdAt;
}
