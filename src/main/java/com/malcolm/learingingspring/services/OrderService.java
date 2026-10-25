package com.malcolm.learingingspring.services;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    final private PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder() {
        this.paymentService.processPayment(10);
    }
}
