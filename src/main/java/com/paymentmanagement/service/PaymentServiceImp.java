package com.paymentmanagement.service;

import com.paymentmanagement.model.Payment;
import com.paymentmanagement.model.PaymentType;
import com.paymentmanagement.repository.PaymentRepository;

import java.util.List;

public class PaymentServiceImp implements PaymentService{
    private final PaymentRepository paymentRepository;

    public PaymentServiceImp(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addSalaryToAgent(Payment payment) throws Exception {
        if(payment.getAmount() < 0) {
            throw new Exception("Amount not valid");
        }

        if(payment.getAgent() == null) {
            throw new Exception("Agent cannot be null");
        }
        payment.setPaymentType(PaymentType.SALARY);
        return paymentRepository.makePayment(payment);
    }

    @Override
    public Payment addBonusToAgent(Payment payment) {
        payment.setPaymentType(PaymentType.BONUS);
        return paymentRepository.makePayment(payment);
    }

    @Override
    public Payment addPrimeToAgent(Payment payment) {
        payment.setPaymentType(PaymentType.PRIME);
        return paymentRepository.makePayment(payment);
    }

    @Override
    public List<Payment> getPaymentByAgent(int agentId) {
        return paymentRepository.getPaymentsByAgent(agentId);
    }
}
