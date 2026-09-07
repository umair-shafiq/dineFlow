package com.dev.dineFlow.controller;

import com.dev.dineFlow.dto.request.PaymentRequestDto;
import com.dev.dineFlow.dto.response.InvoiceResponseDto;
import com.dev.dineFlow.dto.response.PaymentResponseDto;
import com.dev.dineFlow.service.InvoiceService;
import com.dev.dineFlow.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController
{
    private final InvoiceService invoiceService;

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<InvoiceResponseDto>> getAllInvoices()
    {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDto> getInvoiceById(@PathVariable Long id)
    {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<InvoiceResponseDto> getInvoiceByOrderId(@PathVariable Long id)
    {
        return ResponseEntity.ok(invoiceService.getInvoiceByOrderId(id));
    }

    @PostMapping("/{id}/payment")
    public ResponseEntity<PaymentResponseDto> recordPayment(@PathVariable Long id, @Valid @RequestBody PaymentRequestDto requestDto)
    {
        return new ResponseEntity<>(paymentService.recordPayment(id, requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/payment")
    public ResponseEntity<PaymentResponseDto> getPaymentByInvoiceId(@PathVariable Long id)
    {
        return ResponseEntity.ok(paymentService.getPaymentByInvoiceId(id));
    }
}
