package com.hamada.shopservice.service.impl;

import com.hamada.shopservice.entity.Order;
import com.hamada.shopservice.entity.Payment;
import com.hamada.shopservice.entity.PaymentMethod;
import com.hamada.shopservice.entity.PaymentStatus;
import com.hamada.shopservice.repository.OrderRepository;
import com.hamada.shopservice.repository.PaymentRepository;
import com.hamada.shopservice.service.OrderService;
import com.hamada.shopservice.service.PaymentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment updatePayment(Long id, Payment payment) {
        Payment payment1=paymentRepository.findById(id).orElseThrow(()->new RuntimeException("Payment not found"));
        payment1.setAmount(payment.getAmount());
        payment1.setMethod(payment.getMethod());
        payment1.setStatus(payment.getStatus());
        return paymentRepository.save(payment1);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Payment createPayment(Payment payment) {
        Order order=orderRepository.findById(payment.getOrder().getId()).orElseThrow(()->new RuntimeException("Order is not found"));
        payment.setOrder(order);
        Payment savedPayment=paymentRepository.save(payment);
        order.setPayment(savedPayment);
        orderRepository.save(order);
        return savedPayment;
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElseThrow(()->new RuntimeException("Payment not found"));
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findAllByUserId(userId);
    }

    @Override
    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findAllByStatus(status);
    }

    @Override
    public List<Payment> getPaymentsByMethod(PaymentMethod method) {
        return paymentRepository.findAllByMethod(method);
    }

    @Transactional
    @Override
    public Payment processPayment(Long orderId, PaymentMethod method) {
//        Payment payment=new Payment();
        Order order=orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));
        Payment payment=paymentRepository.findByOrder_Id(orderId).orElse(new Payment());
        payment.setMethod(method);
        payment.setOrder(order);
        payment.setAmount(order.getTotalPrice());
        payment.setUserId(order.getUserId());
        payment.setStatus(PaymentStatus.SUCCESS);
        Payment savedPayment=paymentRepository.save(payment);
        order.setPayment(savedPayment);
        orderRepository.save(order);
        return savedPayment;
    }
}
