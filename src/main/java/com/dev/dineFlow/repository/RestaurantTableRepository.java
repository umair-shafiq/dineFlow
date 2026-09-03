package com.dev.dineFlow.repository;

import com.dev.dineFlow.entity.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long>
{
    boolean existsByTableNumberIgnoreCase(String tableNumber);

    boolean existsByTableNumberIgnoreCaseAndRestaurantTableIdNot(String tableNumber, Long id);

}
