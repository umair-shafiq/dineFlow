package com.dev.dineFlow.repository;

import com.dev.dineFlow.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long>
{
    Optional<Payment> findByInvoiceInvoiceId(Long invoiceId);

    boolean existsByInvoiceInvoiceId(Long invoiceId);
}
