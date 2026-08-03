package com.hamada.shopservice.controller;

import com.hamada.shopservice.entity.OrderItem;
import com.hamada.shopservice.service.OrderItemService;
import com.hamada.shopservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public List<OrderItem> getAllOrderItems(){
        return orderItemService.getAllOrderItems();
    }

    @GetMapping("/{id}")
    public OrderItem getOrderItemById(@PathVariable Long id){
        return orderItemService.getOrderItemById(id);
    }

    @GetMapping("/order/{orderId}")
    public List<OrderItem> getOrderItemsByOrderId(@PathVariable Long orderId){
        return orderItemService.getOrderItemsByOrderId(orderId);
    }

    @GetMapping("/product/{productId}")
    public List<OrderItem> getOrderItemsByProductId(@PathVariable Long productId){
        return orderItemService.getOrderItemsByProductId(productId);
    }

    @PostMapping
    public OrderItem createOrderItem(@RequestBody OrderItem orderItem){
        return orderItemService.createOrderItem(orderItem);
    }

    @PutMapping("/{id}")
    public OrderItem updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem){
        return orderItemService.updateOrderItem(id,orderItem);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderItem(@PathVariable Long id){
        orderItemService.deleteOrderItem(id);
    }
}
