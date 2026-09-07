package com.dev.dineFlow.helper;

import com.dev.dineFlow.dto.response.InvoiceResponseDto;
import com.dev.dineFlow.entity.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvoiceMapper
{
    private final OrderMapper orderMapper;

    public InvoiceResponseDto toResponse(Invoice invoice)
    {
        InvoiceResponseDto invoiceResponseDto = new InvoiceResponseDto();
        invoiceResponseDto.setInvoiceId(invoice.getInvoiceId());
        invoiceResponseDto.setOrder(orderMapper.toResponse(invoice.getOrder()));
        invoiceResponseDto.setInvoiceNumber(invoice.getInvoiceNumber());
        invoiceResponseDto.setSubtotal(invoice.getSubtotal());
        invoiceResponseDto.setTaxAmount(invoice.getTaxAmount());
        invoiceResponseDto.setTotalAmount(invoice.getTotalAmount());
        invoiceResponseDto.setPaymentStatus(invoice.getPaymentStatus());
        invoiceResponseDto.setCreatedAt(invoice.getCreatedAt());
        return invoiceResponseDto;
    }
}

