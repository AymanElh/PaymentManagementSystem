package com.paymentmanagement.service;

import com.paymentmanagement.model.Payment;
import com.paymentmanagement.model.PaymentType;
import com.paymentmanagement.repository.PaymentRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public Payment findPaymentById(int paymentId) {
        return paymentRepository.getPaymentById(paymentId);
    }

    @Override
    public List<Payment> getPaymentsByAgent(int agentId) {
        return paymentRepository.getPaymentsByAgent(agentId);
    }

    @Override
    public double calculateTotalOfPayments(int agentId) {
        return paymentRepository.calculateTotalPaymentByAgent(agentId);
    }

    @Override
    public List<Payment> sortPaymentsDESC(int agentId) {
        return paymentRepository.sortPaymentsAscByAgent(agentId);
    }

    @Override
    public List<Payment> filterPaymentsByType(int agentId, String type) {
        System.out.println((agentId));
        return getPaymentsByAgent(agentId)
                .stream()
                .filter(payment -> payment.getPaymentType().name().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public Payment[] getMinMaxPayment(int agentId) {
        List<Payment> payments = getPaymentsByAgent(agentId);
        Payment[] minMaxPayments = new Payment[2];
        minMaxPayments[0] =  payments
                .stream()
                .min(Comparator.comparing(Payment::getAmount))
                .orElse(null);

        minMaxPayments[1] = payments
                .stream()
                .max(Comparator.comparing(Payment::getAmount))
                .orElse(null);

        return minMaxPayments;
    }
}
