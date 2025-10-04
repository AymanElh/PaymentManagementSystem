package com.paymentmanagement.repository;

import com.paymentmanagement.dao.PaymentDAOImp;
import com.paymentmanagement.model.Payment;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentRepository {
    private final PaymentDAOImp paymentDAO;

    public PaymentRepository(PaymentDAOImp paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    public List<Payment> getAllPayments() {
        return paymentDAO.findAll();
    }

    public Payment makePayment(Payment payment) {
        return paymentDAO.save(payment);
    }

    public Payment getPaymentById(int paymentId) {
        return paymentDAO.findById(paymentId);
    }

    public List<Payment> getPaymentsByAgent(int agentId) {
        return paymentDAO.findAll()
                .stream()
                .filter(payment -> payment.getAgent().getId() == agentId)
                .collect(Collectors.toList());
    }

    public List<Payment> getPaymentByDepartment(int departmentId) {
        return paymentDAO.findAll()
                .stream()
                .filter(payment -> payment.getAgent().getDepartment().getId() == departmentId)
                .collect(Collectors.toList());
    }

    public double getAveragePaymentOfDepartment(int departmentId) {
        return paymentDAO.findAll()
                .stream()
                .filter(payment -> payment.getAgent().getDepartment().getId() == departmentId)
                .mapToDouble(Payment::getAmount)
                .average()
                .orElse(0.00);
    }

    public List<Payment> sortPaymentsAscByAgent(int agentId) {
        return getPaymentsByAgent(agentId)
                .stream()
                .sorted(Comparator.comparing(Payment::getAmount).reversed())
                .toList();
    }

    public double calculateTotalPaymentByAgent(int agentId) {
        return getPaymentsByAgent(agentId)
                .stream()
                .mapToDouble(payment -> payment.getAmount())
                .sum();
    }
}
