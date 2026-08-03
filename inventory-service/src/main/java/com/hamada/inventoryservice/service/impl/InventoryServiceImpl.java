package com.hamada.inventoryservice.service.impl;

import com.hamada.inventoryservice.entity.Inventory;
import com.hamada.inventoryservice.repository.InventoryRepository;
import com.hamada.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    @Override
    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElseThrow(()->new RuntimeException("Inventory not found"));
    }

    @Override
    public Optional<Inventory> getInventoryByProductId(Long id) {
        return inventoryRepository.findByProductId(id);
    }

    @Override
    public Inventory createInventory(Inventory inventory) {
        if(inventoryRepository.findByProductId(inventory.getProductId()).isPresent())
            throw new RuntimeException("Product already exists");
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory updateInventory(Long id, Inventory inventory) {
        Inventory inventory1=inventoryRepository.findById(id).orElseThrow(()->new RuntimeException("Inventory not found"));
        inventory1.setQuantity(inventory.getQuantity());
        inventory1.setProductId(inventory.getProductId());
        inventory1.setReservedQuantity(inventory.getReservedQuantity());
        inventory1.setWarehouseLocation(inventory.getWarehouseLocation());
        return inventoryRepository.save(inventory1);
    }

    @Override
    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }

    @Override
    public boolean hasStock(Long productId, int quantity) {
        Inventory inventory=getInventoryByProductId(productId).orElseThrow(()->new RuntimeException("Product not found in inventory"));
        return (inventory.getQuantity()-inventory.getReservedQuantity())>=quantity;
    }

    @Override
    public void reserveStock(Long productId, int quantity) {
        if(hasStock(productId,quantity)){
            Inventory inventory=getInventoryByProductId(productId).orElseThrow(()->new RuntimeException("Product not found in the inventory"));
            inventory.setReservedQuantity(inventory.getReservedQuantity()+quantity);
            inventoryRepository.save(inventory);
        }
        else
            throw new RuntimeException("Product is out of stock");
    }

    @Override
    public void confirmStock(Long productId, int quantity) {
        Inventory inventory=getInventoryByProductId(productId).orElseThrow(()->new RuntimeException("Product is not found in the inventory"));
        if(quantity> inventory.getReservedQuantity())
            throw new RuntimeException("Invalid quantity");
        inventory.setQuantity(inventory.getQuantity()-quantity);
        inventory.setReservedQuantity(inventory.getReservedQuantity()-quantity);
        inventoryRepository.save(inventory);
    }

    @Override
    public void releaseStock(Long productId, int quantity) {
        Inventory inventory=getInventoryByProductId(productId).orElseThrow(()->new RuntimeException("Product not found in the inventory"));
        if(quantity> inventory.getReservedQuantity())
            throw new RuntimeException("Invalid quantity");
        inventory.setReservedQuantity(inventory.getReservedQuantity()-quantity);
        inventoryRepository.save(inventory);
    }
}
