package com.paymentmanagement.dao;

import com.paymentmanagement.model.Agent;

import java.util.List;

public interface AgentDAO {
    Agent save(Agent agent);
    Agent update(Agent agent);
    boolean delete(int id);
    List<Agent> findAll();
    Agent findById(int id);
    Agent findByEmail(String email);
    List<Agent> findByDeparment(String department);
}
