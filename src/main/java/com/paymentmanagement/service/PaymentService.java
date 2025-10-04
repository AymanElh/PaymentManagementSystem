package com.paymentmanagement.service;

import com.paymentmanagement.model.Payment;

import java.util.List;

public interface PaymentService {
    Payment addSalaryToAgent(Payment payment) throws Exception;
    Payment addPrimeToAgent(Payment payment);
    Payment addBonusToAgent(Payment payment);
    Payment findPaymentById(int paymentId);

    List<Payment> getPaymentsByAgent(int agentId);
    double calculateTotalOfPayments(int agentId);
    List<Payment> sortPaymentsDESC(int agentId);
    List<Payment> filterPaymentsByType(int agentId, String type);
    Payment[] getMinMaxPayment(int agentId);
}
