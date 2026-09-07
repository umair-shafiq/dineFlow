package com.dev.dineFlow.helper;

import com.dev.dineFlow.dto.response.PaymentResponseDto;
import com.dev.dineFlow.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper
{
    public PaymentResponseDto toResponse(Payment payment)
    {
        PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
        paymentResponseDto.setPaymentId(payment.getPaymentId());
        paymentResponseDto.setInvoiceId(payment.getInvoice().getInvoiceId());
        paymentResponseDto.setAmountPaid(payment.getAmountPaid());
        paymentResponseDto.setPaymentMethod(payment.getPaymentMethod());
        paymentResponseDto.setPaidAt(payment.getPaidAt());
        return paymentResponseDto;
    }
}