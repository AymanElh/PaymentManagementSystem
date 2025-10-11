package com.paymentmanagement.service;

import com.paymentmanagement.exception.ValidationException;
import com.paymentmanagement.model.Payment;
import com.paymentmanagement.model.PaymentType;
import com.paymentmanagement.repository.PaymentRepository;
import com.paymentmanagement.validation.PaymentValidator;
import com.paymentmanagement.validation.Validator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentServiceImp implements PaymentService{
    private final PaymentRepository paymentRepository;
    private Validator<Payment> paymentValidator;

    public PaymentServiceImp(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentValidator = new PaymentValidator();
    }

    @Override
    public Payment addSalaryToAgent(Payment payment) throws ValidationException {
        payment.setPaymentType(PaymentType.SALARY);
        paymentValidator.validate(payment);
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

    public List<Payment> getPaymentsByMonths(int agentId, int month) {
        List<Payment> paymentsByAgent = getPaymentsByAgent(agentId);
        System.out.println(paymentsByAgent);
        return paymentsByAgent.stream()
                .filter(payment -> {
                    System.out.println(payment.getPaymentDate().getMonth());
                    return payment.getPaymentDate().getMonth()  == month - 1;
                })
                .collect(Collectors.toList());
    }
}
