package com.paymentmanagement.service;

import com.paymentmanagement.model.PaymentType;
import com.paymentmanagement.repository.AgentRepository;
import com.paymentmanagement.repository.DepartmentRepository;
import com.paymentmanagement.repository.PaymentRepository;
import com.paymentmanagement.model.Payment;
import com.paymentmanagement.model.Agent;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class StatisticsServiceImp implements StatisticsService {
    private final PaymentRepository paymentRepository;
    private final AgentRepository agentRepository;
    private final DepartmentRepository departmentRepository;

    public StatisticsServiceImp(PaymentRepository paymentRepository, AgentRepository agentRepository, DepartmentRepository departmentRepository) {
        this.paymentRepository = paymentRepository;
        this.agentRepository = agentRepository;
        this.departmentRepository = departmentRepository;
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

    @Override
    public int nbrTotalOfAgent() {
        return agentRepository.getAllAgents().size();
    }

    @Override
    public int nbrTotalOfDepartments() {
        return departmentRepository.getAllDepartments().size();
    }

    @Override
    public Map<PaymentType, Double> distibutionOfPaymentByType() {
        List<Payment> payments = paymentRepository.getAllPayments();
        Map<PaymentType, Long> countByType = payments.stream()
                .collect(Collectors.groupingBy(Payment::getPaymentType, Collectors.counting()));

        System.out.println(countByType);
        int total = payments.size();

        return countByType.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> (entry.getValue() * 100.0) / total));
    }
}
