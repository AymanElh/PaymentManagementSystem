package com.paymentmanagement.service;

import com.paymentmanagement.model.Agent;

import java.util.List;
import java.util.Optional;

public interface AgentService {
    Agent addEmployee(Agent agent) throws Exception;
    Agent addManager(Agent agent) throws Exception;
    Agent updateDepartmentManager(Agent newManager, int departmentId);
    Agent desactivateAgent(int agentId) throws Exception;
    List<Agent> getEmployeesOnDepartment(int departmentId);
    Optional<Agent> getDepartmentManager(int departmentId);
    List<Agent> getAllManagers();
}