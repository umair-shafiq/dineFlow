package com.dev.dineFlow.dto.request;

import com.dev.dineFlow.entity.enums.PaymentMethodEnums;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentRequestDto
{
    @NotNull
    @Positive
    private Double amountPaid;

    @NotNull
    private PaymentMethodEnums paymentMethod;

}
