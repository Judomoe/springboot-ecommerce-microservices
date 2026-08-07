package com.hamada.shopservice.service.impl;

import com.hamada.shopservice.client.InventoryClient;
import com.hamada.shopservice.entity.Order;
import com.hamada.shopservice.entity.OrderItem;
import com.hamada.shopservice.entity.OrderStatus;
import com.hamada.shopservice.repository.OrderRepository;
import com.hamada.shopservice.service.OrderItemService;
import com.hamada.shopservice.service.OrderService;
import com.hamada.shopservice.service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(()->new RuntimeException("Order not found"));
    }

    @CircuitBreaker(name = "inventoryService", fallbackMethod = "createOrderFallback")
    @Transactional
    @Override
    public Order createOrder(Order order) {
        if(order.getOrderItem()==null){
            throw new RuntimeException("NULLLLLLL");
        }
        for(OrderItem orderItem:order.getOrderItem()){
            Long productId=orderItem.getProduct().getId();
            String name=orderItem.getProduct().getName();
            int quantity=orderItem.getQuantity();
            if(inventoryClient.hasStock(productId,quantity)){
                inventoryClient.reserveStock(productId,quantity);
            }
            else{
                throw new RuntimeException("Insufficient stock for product: "+name);
            }
            orderItem.setOrder(order);
        }
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    public Order createOrderFallback(Order order, Exception ex) {
        throw new RuntimeException(
                "Inventory service is currently unavailable. Please try again later."
        );
    }



    @Override
    public Order updateOrder(Long id, Order order) {
        Order order1=orderRepository.findById(id).orElseThrow(()->new RuntimeException("Order not found"));
        order1.setStatus(order.getStatus());
        order1.setTotalPrice(order.getTotalPrice());
        return orderRepository.save(order1);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> getOrderByUserId(Long userId) {
        return orderRepository.findOrderByUserId(userId);
    }

    @Override
    public List<Order> getOrderByStatus(OrderStatus status) {
        return orderRepository.findOrderByStatus(status);
    }

    @CircuitBreaker(name = "inventoryService", fallbackMethod = "createOrderFallback")
    @Transactional
    @Override
    public Order confirmOrder(Long orderId) {
        Order order=orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be confirmed");
        }
        for (OrderItem orderItem : order.getOrderItem()) {
            Long productId = orderItem.getProduct().getId();
            int quantity = orderItem.getQuantity();
            inventoryClient.confirmStock(productId, quantity);
        }
        order.setStatus(OrderStatus.PAID);
        return orderRepository.save(order);
    }

    @CircuitBreaker(name = "inventoryService", fallbackMethod = "createOrderFallback")
    @Transactional
    @Override
    public Order cancelOrder(Long orderId) {
        Order order=orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be canceled");
        }
        for (OrderItem orderItem : order.getOrderItem()) {
            Long productId = orderItem.getProduct().getId();
            int quantity = orderItem.getQuantity();
            inventoryClient.releaseStock(productId, quantity);
        }
        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}
