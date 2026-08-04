package com.hamada.shopservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("inventory-service")
public interface InventoryClient {
    @GetMapping("/inventory/has-stock/{productId}")
    public boolean hasStock(@PathVariable Long productId, @RequestParam int quantity);

    @PutMapping("/inventory/reserve/{productId}")
    public void reserveStock(@PathVariable Long productId, @RequestParam int quantity);

    @PutMapping("/inventory/confirm/{productId}")
    public void confirmStock(@PathVariable Long productId, @RequestParam int quantity);

    @PutMapping("/inventory/release/{productId}")
    public void releaseStock(@PathVariable Long productId, @RequestParam int quantity);
}
