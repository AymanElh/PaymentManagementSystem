package com.paymentmanagement.service;

import com.paymentmanagement.repository.PaymentRepository;
import com.paymentmanagement.model.Payment;
import com.paymentmanagement.model.Agent;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class StatisticsServiceImp implements StatisticsService {
    private final PaymentRepository paymentRepository;

    public StatisticsServiceImp(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public int getTotalPaymentsOfDepartment(int departmentId) {
        List<Payment> paymentsByDep = paymentRepository.getPaymentByDepartment(departmentId);
        return paymentsByDep.size();
    }

    @Override
    public double getAveragePaymentOfDepartment(int departmentId) {
        return paymentRepository.getAveragePaymentOfDepartment(departmentId);
    }

    @Override
    public List<Agent> getRankingAgentsByTotalPayments(int departmentId) {
        List<Payment> allPaymentsInDep = paymentRepository.getPaymentByDepartment(departmentId);

        // Group payments by agent ID and sum their amounts
        Map<Integer, Double> agentTotalMap = allPaymentsInDep.stream()
            .collect(Collectors.groupingBy(
                payment -> payment.getAgent().getId(),
                Collectors.summingDouble(Payment::getAmount)
            ));

        Map<Integer, Agent> agentMap = allPaymentsInDep.stream()
            .collect(Collectors.toMap(
                payment -> payment.getAgent().getId(),
                payment -> payment.getAgent(),
                (existing, replacement) -> existing
            ));

        return agentTotalMap.entrySet().stream()
            .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
            .map(entry -> agentMap.get(entry.getKey()))
            .collect(Collectors.toList());
    }

}
