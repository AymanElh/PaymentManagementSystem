package com.paymentmanagement.model;

import java.util.List;

public class Agent extends User {
    private AgentType agentType;
    private Department department;
    private List<Payment> payments;

    public Agent(String firstName, String lastName, String email, String password, String phone, AgentType agentType, Department department, List<Payment> payments) {
        super(firstName, lastName, email, password, phone);
        this.agentType = agentType;
        this.department = department;
        this.payments = payments;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public AgentType getAgentType() {
        return agentType;
    }

    public void setAgentType(AgentType agentType) {
        this.agentType = agentType;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "agentType=" + agentType +
                ", department=" + department +
                ", payments=" + payments +
                '}';
    }
}
