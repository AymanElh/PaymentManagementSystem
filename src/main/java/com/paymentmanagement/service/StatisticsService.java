package com.paymentmanagement.service;

import com.paymentmanagement.model.Agent;
import java.util.List;

public interface StatisticsService {
    int getTotalPaymentsOfDepartment(int departmentId);
    double getAveragePaymentOfDepartment(int departmentId);
    List<Agent> getRankingAgentsByTotalPayments(int departmentId);
}
