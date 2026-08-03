package com.hamada.inventoryservice.controller;


import com.hamada.inventoryservice.entity.Inventory;
import com.hamada.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<Inventory> getAllInventory(){
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{id}")
    public Inventory getInventoryById(@PathVariable Long id){
        return inventoryService.getInventoryById(id);
    }

    @GetMapping("/product/{id}")
    public Optional<Inventory> getInventoryByProductId(@PathVariable Long id){
        return inventoryService.getInventoryByProductId(id);
    }

    @GetMapping("/has-stock/{productId}")
    public boolean hasStock(@PathVariable Long productId, @RequestParam int quantity){
        return inventoryService.hasStock(productId,quantity);
    }

    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory){
        return inventoryService.createInventory(inventory);
    }

    @PutMapping("/reserve/{productId}")
    public void reserveStock(@PathVariable Long productId,@RequestParam int quantity){
        inventoryService.reserveStock(productId,quantity);
    }

    @PutMapping("/confirm/{productId}")
    public void confirmStock(@PathVariable Long productId,@RequestParam int quantity){
        inventoryService.confirmStock(productId,quantity);
    }

    @PutMapping("/release/{productId}")
    public void releaseStock(@PathVariable Long productId,@RequestParam int quantity){
        inventoryService.releaseStock(productId,quantity);
    }

    @PutMapping("/{id}")
    public Inventory updateInventory(@PathVariable Long id, @RequestBody Inventory inventory){
        return inventoryService.updateInventory(id,inventory);
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable Long id){
        inventoryService.deleteInventory(id);
    }
}
