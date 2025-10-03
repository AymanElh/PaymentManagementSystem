package com.paymentmanagement.repository;

import com.paymentmanagement.dao.AgentDAO;
import com.paymentmanagement.dao.DepartmentDAO;
import com.paymentmanagement.model.Agent;
import com.paymentmanagement.model.AgentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AgentRepository {
    private final AgentDAO agentDAO;

    public AgentRepository(AgentDAO agentDAO) {
        this.agentDAO = agentDAO;
    }

    public Agent getAgentById(int id) {
        return agentDAO.findById(id);
    }

    public Agent getAgentByEmail(String email) {
        return agentDAO.findByEmail(email);
    }

    public Agent createAgent(Agent agent) {
        return agentDAO.save(agent);
    }

    public Agent updateAgent(Agent agent) {
        return agentDAO.update(agent);
    }

    public boolean deleteAgent(int id) {
        return agentDAO.delete(id);
    }

    public List<Agent> getEmployeesByDepartmentId(int departmentId) {
        return agentDAO.findByDepartment(departmentId)
                .stream()
                .filter(employee -> employee.getAgentType() == AgentType.EMPLOYEE)
                .collect(Collectors.toList());
    }

    public Optional<Agent> getDepartmentManager(int departmentId) {
        return agentDAO.findByDepartment(departmentId)
                .stream()
                .filter(agent -> agent.getAgentType() == AgentType.MANAGER)
                .findFirst();
    }

    public List<Agent> getActiveAgents() {
        return agentDAO.findAll()
                .stream()
                .filter(agent -> agent.getIsActive() == true)
                .collect(Collectors.toList());
    }

    public List<Agent> getAllAgents() {
        return agentDAO.findAll();
    }

    public List<Agent> getAllManagers() {
        return agentDAO.findAll()
                .stream()
                .filter(agent -> agent.getAgentType() == AgentType.MANAGER)
                .collect(Collectors.toList());
    }

}
