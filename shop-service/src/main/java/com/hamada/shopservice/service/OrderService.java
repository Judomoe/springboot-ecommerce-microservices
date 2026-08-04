package com.hamada.shopservice.service;

import com.hamada.shopservice.entity.Order;
import com.hamada.shopservice.entity.OrderItem;
import com.hamada.shopservice.entity.OrderStatus;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order getOrderById(Long id);

    Order createOrder(Order order);

    Order updateOrder(Long id, Order order);

    void deleteOrder(Long id);

    List<Order> getOrderByUserId(Long userId);

    List<Order> getOrderByStatus(OrderStatus status);

    Order confirmOrder(Long orderId);

    Order cancelOrder(Long orderId);
}
