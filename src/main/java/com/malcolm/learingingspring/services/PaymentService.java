package com.malcolm.learingingspring.services;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public void processPayment(int amount) {
        System.out.println("PaymentService processPayment; Amount: " + amount);
    }
}
