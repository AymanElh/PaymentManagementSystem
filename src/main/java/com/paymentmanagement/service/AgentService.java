package com.paymentmanagement.service;

import com.paymentmanagement.model.Agent;

public interface AgentService {
    Agent addEmployee(Agent agent) throws Exception;
    Agent addManager(Agent agent) throws Exception;
    Agent desactivateAgent(int agentId) throws Exception;
}