package com.hamada.shopservice.service.impl;

import com.hamada.shopservice.entity.OrderItem;
import com.hamada.shopservice.repository.OrderItemRepository;
import com.hamada.shopservice.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(()->new RuntimeException("Order-item not found"));
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem updateOrderItem(Long id, OrderItem orderItem) {
        OrderItem orderItem1=orderItemRepository.findById(id).orElseThrow(()->new RuntimeException("Order-item not found"));
        orderItem1.setPrice(orderItem.getPrice());
        orderItem1.setQuantity(orderItem.getQuantity());
        return orderItemRepository.save(orderItem1);
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findAllByOrder_Id(orderId);
    }

    @Override
    public List<OrderItem> getOrderItemsByProductId(Long productId) {
        return orderItemRepository.findAllByProduct_Id(productId);
    }
}
