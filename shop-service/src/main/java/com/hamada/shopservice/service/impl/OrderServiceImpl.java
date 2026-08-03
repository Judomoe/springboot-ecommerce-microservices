package com.hamada.shopservice.service.impl;

import com.hamada.shopservice.entity.Order;
import com.hamada.shopservice.entity.OrderStatus;
import com.hamada.shopservice.repository.OrderRepository;
import com.hamada.shopservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(()->new RuntimeException("Order not found"));
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
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
}
