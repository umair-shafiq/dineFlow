package com.dev.dineFlow.repository;

import com.dev.dineFlow.dto.response.CategoryRevenueResponseDto;
import com.dev.dineFlow.dto.response.MostOrderedItemResponseDto;
import com.dev.dineFlow.dto.response.SalesReportResponseDto;
import com.dev.dineFlow.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface ReportRepository extends JpaRepository<Order, Long>
{
    @Query("""
            SELECT new com.dev.dineFlow.dto.response.SalesReportResponseDto(
                                          COUNT(o),
                                          COALESCE(SUM(o.totalAmount), 0.0),
                                          COALESCE(SUM(o.taxAmount), 0.0)
                                      ) FROM Order o
                                                  WHERE o.orderStatus = com.dev.dineFlow.entity.enums.OrderStatusEnums.COMPLETED
                                                  AND o.createdAt BETWEEN :startDate AND :endDate
            """)
    SalesReportResponseDto getSalesReport(@Param("startDate") LocalDateTime from, @Param("endDate") LocalDateTime to);

    @Query("""
            SELECT new com.dev.dineFlow.dto.response.MostOrderedItemResponseDto(
                oi.menuItem.name,
                SUM(oi.quantity)
            )
            FROM Order o
            JOIN o.orderItems oi
            WHERE o.orderStatus = com.dev.dineFlow.entity.enums.OrderStatusEnums.COMPLETED
            GROUP BY oi.menuItem.name
            ORDER BY SUM(oi.quantity) DESC
            """)
    List<MostOrderedItemResponseDto> getMostOrderedItems(Pageable pageable);

    @Query("""
            SELECT new com.dev.dineFlow.dto.response.CategoryRevenueResponseDto(
                oi.menuItem.category.name,
                    SUM(oi.subtotal)
            )
            FROM Order o
            JOIN o.orderItems oi
            WHERE o.orderStatus = com.dev.dineFlow.entity.enums.OrderStatusEnums.COMPLETED
            GROUP BY oi.menuItem.category.name
            ORDER BY SUM(oi.subtotal) DESC
            """)
    List<CategoryRevenueResponseDto> getCategoryRevenue();

    @Query("""
            SELECT o.createdAt FROM Order o
            WHERE o.orderStatus = com.dev.dineFlow.entity.enums.OrderStatusEnums.COMPLETED
            """)
    List<LocalDateTime> findAllCompletedOrderTimestamps();
}



