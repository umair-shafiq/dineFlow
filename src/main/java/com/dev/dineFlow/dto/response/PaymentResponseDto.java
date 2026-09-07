package com.dev.dineFlow.dto.response;

import com.dev.dineFlow.entity.enums.PaymentMethodEnums;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentResponseDto
{

    private Long paymentId;

    private Long invoiceId;

    private double amountPaid;

    private PaymentMethodEnums paymentMethod;

    private LocalDateTime paidAt;
}
