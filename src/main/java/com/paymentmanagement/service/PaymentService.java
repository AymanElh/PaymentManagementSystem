package com.paymentmanagement.service;

import com.paymentmanagement.model.Payment;

import java.util.List;

public interface PaymentService {
    Payment addSalaryToAgent(Payment payment) throws Exception;
    Payment addPrimeToAgent(Payment payment);
    Payment addBonusToAgent(Payment payment);

    List<Payment> getPaymentByAgent(int agentId);
}
