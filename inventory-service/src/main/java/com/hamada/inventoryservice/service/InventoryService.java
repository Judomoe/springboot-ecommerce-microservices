package com.hamada.inventoryservice.service;

import com.hamada.inventoryservice.entity.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    List<Inventory> getAllInventory();

    Inventory getInventoryById(Long id);

    Optional<Inventory> getInventoryByProductId(Long id);

    Inventory createInventory(Inventory inventory);

    Inventory updateInventory(Long id, Inventory inventory);

    void deleteInventory(Long id);

    boolean hasStock(Long productId, int quantity);

    void reserveStock(Long productId, int quantity);

    void confirmStock(Long productId, int quantity);

    void releaseStock(Long productId, int quantity);
}
