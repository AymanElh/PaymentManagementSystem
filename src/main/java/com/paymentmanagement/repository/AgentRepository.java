package com.paymentmanagement.repository;

import com.paymentmanagement.dao.AgentDAO;
import com.paymentmanagement.dao.DepartmentDAO;
import com.paymentmanagement.model.Agent;
import com.paymentmanagement.model.AgentType;

import java.util.List;
import java.util.stream.Collectors;

public class AgentRepository {
    private final AgentDAO agentDAO;
    private final DepartmentDAO departmentDAO;

    public AgentRepository(AgentDAO agentDAO, DepartmentDAO departmentDAO) {
        this.agentDAO = agentDAO;
        this.departmentDAO = departmentDAO;
    }

    public List<Agent> getActiveAgents() {
        return agentDAO.findAll()
                .stream()
                .filter(agent -> agent.getIsActive() == true)
                .collect(Collectors.toList());
    }

    public List<Agent> getAllManagers() {
        return agentDAO.findAll()
                .stream()
                .filter(agent -> agent.getAgentType() == AgentType.MANAGER)
                .collect(Collectors.toList());
    }


}
