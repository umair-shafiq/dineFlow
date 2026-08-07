package com.dev.dineFlow.repository;

import com.dev.dineFlow.entity.Order;
import com.dev.dineFlow.entity.enums.OrderStatusEnums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>
{
    List<Order> findByOrderStatusIn(List<OrderStatusEnums> statuses);

    Optional<Order> findByOrderNumber(String number);
}
