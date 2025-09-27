package com.paymentmanagement.dao;

import com.paymentmanagement.model.Agent;

import java.util.List;

public interface AgentDAO extends GenericDAO<Agent> {
    Agent findByEmail(String email);
    List<Agent> findByDepartment(String department);
}
