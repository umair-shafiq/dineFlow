package com.dev.dineFlow.repository;

import com.dev.dineFlow.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>
{
    @Query("""
            SELECT r FROM Reservation r
            WHERE r.restaurantTable.restaurantTableId = :tableId
            AND r.status <> com.dev.dineFlow.entity.enums.ReservationStatusEnums.CANCELLED
            AND r.reservationDateTime BETWEEN :windowStart AND :windowEnd
            """)
    List<Reservation> findConflictingReservations(
            @Param("tableId") Long tableId,
            @Param("windowStart") LocalDateTime windowStart,
            @Param("windowEnd") LocalDateTime windowEnd
    );

    @Query("""
            SELECT r FROM Reservation r
            WHERE r.restaurantTable.restaurantTableId = :tableId
            AND r.reservationId <> :excludeReservationId
            AND r.status <> com.dev.dineFlow.entity.enums.ReservationStatusEnums.CANCELLED
            AND r.reservationDateTime BETWEEN :windowStart AND :windowEnd
            """)
    List<Reservation> findConflictingReservationsExcludingSelf(
            @Param("tableId") Long tableId,
            @Param("excludeReservationId") Long excludeReservationId,
            @Param("windowStart") LocalDateTime windowStart,
            @Param("windowEnd") LocalDateTime windowEnd
    );
}
