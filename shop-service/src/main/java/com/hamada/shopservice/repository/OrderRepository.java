package com.hamada.shopservice.repository;

import com.hamada.shopservice.entity.Order;
import com.hamada.shopservice.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findOrderByUserId(Long userId);

    List<Order> findOrderByStatus(OrderStatus status);
}
