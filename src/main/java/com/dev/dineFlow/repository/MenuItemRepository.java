package com.dev.dineFlow.repository;

import com.dev.dineFlow.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long>
{
    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndMenuItemIdNot(String name, Long menuItemId);
}
