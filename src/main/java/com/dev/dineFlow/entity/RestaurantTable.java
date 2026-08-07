package com.dev.dineFlow.entity;

import com.dev.dineFlow.entity.enums.TableStatusEnums;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "restaurant_table")
@Getter
@Setter
public class RestaurantTable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_table_id")
    private Long restaurantTableId;

    @Column(name = "table_number", nullable = false)
    private String tableNumber;

    @Column(nullable = false)
    private int capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "table_status", nullable = false)
    private TableStatusEnums tableStatus = TableStatusEnums.FREE;
}
