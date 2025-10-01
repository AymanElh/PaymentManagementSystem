package com.paymentmanagement.service;

import com.paymentmanagement.model.Agent;
import com.paymentmanagement.model.AgentType;
import com.paymentmanagement.repository.AgentRepository;

import java.util.List;
import java.util.Optional;

public class AgentServiceImp implements AgentService  {
    private final AgentRepository agentRepository;

    public AgentServiceImp(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public Agent addEmployee(Agent agent) throws Exception {
        if(!agent.getEmail().contains("@")) {
            throw new Exception("Email not valid");
        }

        if(agent.getPassword().length() < 6) {
            throw new Exception("Password invalid");
        }
        agent.setAgentType(AgentType.EMPLOYEE);
        return agentRepository.createAgent(agent);
    }

    @Override
    public Agent addManager(Agent manager) throws Exception {
        if(!manager.getEmail().contains("@")) {
            throw new Exception("Email not valid");
        }

        if(manager.getPassword().length() < 6) {
            throw new Exception("Password invalid");
        }
        manager.setAgentType(AgentType.MANAGER);
        return agentRepository.createAgent(manager);
    }


    @Override
    public Agent desactivateAgent(int agentId) throws Exception {
        Agent agent = agentRepository.getAgentById(agentId);
        if(agent == null) {
            throw new Exception("Agent not found");
        }

        agent.setActive(false);
        return agentRepository.updateAgent(agent);
    }

    @Override
    public List<Agent> getEmployeesOnDepartment(int departmentId) {
        return agentRepository.getEmployeesByDepartmentId(departmentId);
    }

    @Override
    public Optional<Agent> getDepartmentManager(int departmentId) {
        return agentRepository.getDepartmentManager(departmentId);
    }
}
