package com.dev.dineFlow.service;

import com.dev.dineFlow.dto.request.PaymentRequestDto;
import com.dev.dineFlow.dto.response.PaymentResponseDto;
import com.dev.dineFlow.entity.Invoice;
import com.dev.dineFlow.entity.Payment;
import com.dev.dineFlow.entity.enums.PaymentStatusEnums;
import com.dev.dineFlow.exception.DuplicateResourceException;
import com.dev.dineFlow.exception.ResourceNotFoundException;
import com.dev.dineFlow.helper.PaymentMapper;
import com.dev.dineFlow.repository.InvoiceRepository;
import com.dev.dineFlow.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService
{
    private final PaymentRepository paymentRepository;

    private final InvoiceRepository invoiceRepository;

    private final PaymentMapper paymentMapper;

    @Transactional
    public PaymentResponseDto recordPayment(Long id, PaymentRequestDto requestDto)
    {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invoice Not Found."));

        if (invoice.getPaymentStatus() == PaymentStatusEnums.PAID)
        {
            throw new IllegalArgumentException("This invoice has already been paid.");
        }

        if (paymentRepository.existsByInvoiceInvoiceId(id))
        {
            throw new DuplicateResourceException("A payment already exists for this invoice.");
        }

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setAmountPaid(requestDto.getAmountPaid());
        payment.setPaymentMethod(requestDto.getPaymentMethod());
        Payment savedPayment = paymentRepository.save(payment);

        invoice.setPaymentStatus(PaymentStatusEnums.PAID);
        invoiceRepository.save(invoice);

        return paymentMapper.toResponse(savedPayment);
    }

    public PaymentResponseDto getPaymentByInvoiceId(Long id)
    {
        Payment payment = paymentRepository.findByInvoiceInvoiceId(id).orElseThrow(() -> new ResourceNotFoundException("Payment Not Found."));
        return paymentMapper.toResponse(payment);
    }
}
