package com.hamada.inventoryservice.repository;

import com.hamada.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    List<Inventory> findAllByProductId(Long productId);

    Optional<Inventory> findByProductId(Long productId);
}
