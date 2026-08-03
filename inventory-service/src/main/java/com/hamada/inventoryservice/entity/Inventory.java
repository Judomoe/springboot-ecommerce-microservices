package com.hamada.inventoryservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private int quantity;
    private int reservedQuantity;
    private String warehouseLocation;

    public Inventory() {
    }

    public Inventory(Long id, Long productId, int quantity, int reservedQuantity, String warehouseLocation) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.reservedQuantity = reservedQuantity;
        this.warehouseLocation = warehouseLocation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(int reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }
}
