package com.hamada.shopservice.controller;

import com.hamada.shopservice.entity.Payment;
import com.hamada.shopservice.entity.PaymentMethod;
import com.hamada.shopservice.entity.PaymentStatus;
import com.hamada.shopservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public List<Payment> getAllPayments(){
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id){
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Payment> getPaymentsByUserId(@PathVariable Long userId){
        return paymentService.getPaymentsByUserId(userId);
    }

    @GetMapping("/status/{status}")
    public List<Payment> getPaymentsByStatus(@PathVariable PaymentStatus status){
        return paymentService.getPaymentsByStatus(status);
    }

    @GetMapping("/method/{method}")
    public List<Payment> getPaymentsByMethod(@PathVariable PaymentMethod method){
        return paymentService.getPaymentsByMethod(method);
    }

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment){
        return paymentService.createPayment(payment);
    }

    @PutMapping("/{id}")
    public Payment updatePayment(@PathVariable Long id,@RequestBody Payment payment){
        return paymentService.updatePayment(id,payment);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id){
        paymentService.deletePayment(id);
    }

    @PostMapping("/process/{orderId}")
    public Payment processPayment(@PathVariable Long orderId, @RequestParam PaymentMethod method){
        return paymentService.processPayment(orderId,method);
    }
}
