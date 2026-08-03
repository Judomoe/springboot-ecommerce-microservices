package com.hamada.shopservice.repository;

import com.hamada.shopservice.entity.Payment;
import com.hamada.shopservice.entity.PaymentMethod;
import com.hamada.shopservice.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findAllByUserId(Long userId);

    List<Payment> findAllByStatus(PaymentStatus status);

    List<Payment> findAllByMethod(PaymentMethod method);

    Optional<Payment> findByOrder_Id(Long orderId);
}
