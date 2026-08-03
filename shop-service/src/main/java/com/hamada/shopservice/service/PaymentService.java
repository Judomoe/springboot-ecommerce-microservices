package com.hamada.shopservice.service;

import com.hamada.shopservice.entity.Payment;
import com.hamada.shopservice.entity.PaymentMethod;
import com.hamada.shopservice.entity.PaymentStatus;

import java.util.List;

public interface PaymentService {
    Payment updatePayment(Long id, Payment payment);

    void deletePayment(Long id);

    Payment createPayment(Payment payment);

    Payment getPaymentById(Long id);

    List<Payment> getAllPayments();

    List<Payment> getPaymentsByUserId(Long userId);

    List<Payment> getPaymentsByStatus(PaymentStatus status);

    List<Payment> getPaymentsByMethod(PaymentMethod method);

    Payment processPayment(Long orderId, PaymentMethod method);
}
