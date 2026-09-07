package com.dev.dineFlow.repository;

import com.dev.dineFlow.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>
{
    Optional<Invoice> findByOrderOrderId(Long id);

    boolean existsByOrderOrderId(Long id);
}

