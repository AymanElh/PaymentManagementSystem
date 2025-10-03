package com.paymentmanagement.service;

import com.paymentmanagement.exception.EntityNotFoundException;
import com.paymentmanagement.exception.ValidationException;
import com.paymentmanagement.model.Agent;
import com.paymentmanagement.model.AgentType;
import com.paymentmanagement.repository.AgentRepository;
import com.paymentmanagement.validation.AgentValidator;
import com.paymentmanagement.validation.Validator;

import java.util.List;
import java.util.Optional;

public class AgentServiceImp implements AgentService {
    private final AgentRepository agentRepository;
    private final Validator<Agent> agentValidator;

    public AgentServiceImp(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
        this.agentValidator = new AgentValidator();
    }

    @Override
    public Agent addEmployee(Agent agent) throws ValidationException {
        agentValidator.validate(agent);
        agent.setAgentType(AgentType.EMPLOYEE);
        return agentRepository.createAgent(agent);
    }

    @Override
    public Agent addManager(Agent manager) throws ValidationException {
        agentValidator.validate(manager);
        manager.setAgentType(AgentType.MANAGER);
        return agentRepository.createAgent(manager);
    }


    @Override
    public Agent desactivateAgent(int agentId) throws Exception {
        Agent agent = agentRepository.getAgentById(agentId);
        if (agent == null) {
            throw new EntityNotFoundException("Agent", agentId);
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
