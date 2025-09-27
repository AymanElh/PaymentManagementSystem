package com.paymentmanagement.repository;

import com.paymentmanagement.dao.PaymentDAOImp;
import com.paymentmanagement.model.Payment;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentRepository {
    private final PaymentDAOImp paymentDAO;

    public PaymentRepository(PaymentDAOImp paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    public Payment makePayment(Payment payment) {
        return paymentDAO.save(payment);
    }

    public List<Payment> getPaymentsByAgent(int agentId) {
        return paymentDAO.findAll()
                .stream()
                .filter(payment -> payment.getAgent().getId() == agentId)
                .collect(Collectors.toList());
    }

    public List<Payment> sortPaymentsAscByAgent(int agentId) {
        return getPaymentsByAgent(agentId)
                .stream()
                .sorted()
                .toList();
    }

    public double calculateTotalPaymentByAgent(int agentId) {
        return getPaymentsByAgent(agentId)
                .stream()
                .mapToDouble(payment -> payment.getAmount())
                .sum();
    }
}
