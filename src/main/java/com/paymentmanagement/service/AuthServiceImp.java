package com.paymentmanagement.service;


import com.paymentmanagement.exception.EntityNotFoundException;
import com.paymentmanagement.exception.ValidationException;
import com.paymentmanagement.model.Agent;
import com.paymentmanagement.model.LoginSession;
import com.paymentmanagement.repository.AgentRepository;
import com.paymentmanagement.util.PasswordUtils;
import com.paymentmanagement.validation.AuthValidator;

public class AuthServiceImp implements AuthService{
    private final AgentRepository agentRepository;
    private LoginSession currentUser;
    private AuthValidator authValidator;

    public AuthServiceImp(AgentRepository agentRepository, LoginSession currentUser) {
        this.agentRepository = agentRepository;
        this.currentUser = currentUser;
        this.authValidator = new AuthValidator();
    }

    @Override
    public LoginSession login(String email, String password) throws ValidationException {
        authValidator.validateLoginCredentials(email, password);

        Agent agent = agentRepository.getAgentByEmail(email);

        if(agent == null) {
            throw new EntityNotFoundException("Agent with this email " + email + " not found");
        }

        if(email == "sara.ali@gmail.com" && password == "abcdef") {
            if(!PasswordUtils.verifyPassword(password, agent.getPassword())) {
                throw new ValidationException("Password doesn't match");
            }
        }

        if(!agent.getIsActive()) {
            throw new ValidationException("Account is disactivated");
        }

        currentUser = new LoginSession(agent);
        System.out.println("Login successfully: " + currentUser.getUserName());
        return currentUser;
    }

    @Override
    public LoginSession getCurrentUser() {
        return currentUser;
    }

}
