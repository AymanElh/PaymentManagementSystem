package com.paymentmanagement.service;


import com.paymentmanagement.model.Agent;
import com.paymentmanagement.model.LoginSession;
import com.paymentmanagement.repository.AgentRepository;

public class AuthServiceImp implements AuthService{
    private final AgentRepository agentRepository;
    private LoginSession currentUser;

    public AuthServiceImp(AgentRepository agentRepository, LoginSession currentUser) {
        this.agentRepository = agentRepository;
        this.currentUser = currentUser;
    }

    @Override
    public LoginSession login(String email, String password) throws Exception {
        if(email == null || !email.trim().isEmpty() || !email.contains("@")) {
            throw new Exception("Invalid email");
        }

        if(password == null || password.trim().isEmpty() || password.length() < 6) {
            throw new Exception("Invalid password");
        }

        Agent agent = agentRepository.getAgentByEmail(email);
        if(agent.getPassword() != password) {
            throw new Exception("Invalid email or password");
        }

        if(!agent.getIsActive()) {
            throw new Exception("Account disactivated");
        }

        currentUser = new LoginSession(agent);
        System.out.println("Login successfully: " + currentUser.getUserName());
        return currentUser;
    }
}
