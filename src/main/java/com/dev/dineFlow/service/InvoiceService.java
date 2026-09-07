package com.dev.dineFlow.service;

import com.dev.dineFlow.dto.response.InvoiceResponseDto;
import com.dev.dineFlow.entity.Invoice;
import com.dev.dineFlow.entity.Order;
import com.dev.dineFlow.entity.enums.OrderStatusEnums;
import com.dev.dineFlow.exception.DuplicateResourceException;
import com.dev.dineFlow.exception.ResourceNotFoundException;
import com.dev.dineFlow.helper.InvoiceMapper;
import com.dev.dineFlow.repository.InvoiceRepository;
import com.dev.dineFlow.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService
{
    private final InvoiceRepository invoiceRepository;

    private final OrderRepository orderRepository;

    private final InvoiceMapper invoiceMapper;

    public InvoiceResponseDto generateInvoice(Long orderId)
    {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        if (order.getOrderStatus() == OrderStatusEnums.CANCELLED)
        {
            throw new IllegalArgumentException("Cannot generate invoice for a cancelled order.");
        }

        if (invoiceRepository.existsByOrderOrderId(orderId))
        {
            throw new DuplicateResourceException("An invoice already exists for this order.");
        }

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setInvoiceNumber("INV-" + order.getOrderId() + System.currentTimeMillis());
        invoice.setSubtotal(order.getSubtotal());
        invoice.setTaxAmount(order.getTaxAmount());
        invoice.setTotalAmount(order.getTotalAmount());
        return invoiceMapper.toResponse(invoiceRepository.save(invoice));
    }

    public List<InvoiceResponseDto> getAllInvoices()
    {
        return invoiceRepository.findAll().stream().map(invoiceMapper::toResponse).toList();
    }

    public InvoiceResponseDto getInvoiceById(Long id)
    {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice Not Found."));
        return invoiceMapper.toResponse(invoice);
    }

    public InvoiceResponseDto getInvoiceByOrderId(Long id)
    {
        Invoice invoice = invoiceRepository.findByOrderOrderId(id).orElseThrow(() -> new ResourceNotFoundException("Invoice Not Found."));
        return invoiceMapper.toResponse(invoice);
    }
}


