package com.paymentmanagement.service;

import com.paymentmanagement.model.Agent;
import com.paymentmanagement.model.PaymentType;

import java.util.List;
import java.util.Map;

public interface StatisticsService {
    int getTotalPaymentsOfDepartment(int departmentId);
    double getAveragePaymentOfDepartment(int departmentId);
    List<Agent> getRankingAgentsByTotalPayments(int departmentId);
    int nbrTotalOfAgent();
    int nbrTotalOfDepartments();
    Map<PaymentType, Double> distibutionOfPaymentByType();
}
