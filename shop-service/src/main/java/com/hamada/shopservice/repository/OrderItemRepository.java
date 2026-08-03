package com.hamada.shopservice.repository;

import com.hamada.shopservice.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findAllByOrder_Id(Long orderId);

    List<OrderItem> findAllByProduct_Id(Long productId);
}
