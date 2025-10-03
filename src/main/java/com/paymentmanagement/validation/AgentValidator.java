package com.paymentmanagement.validation;

import com.paymentmanagement.exception.ValidationException;
import com.paymentmanagement.model.Agent;

public class AgentValidator implements Validator<Agent> {

    @Override
    public void validate(Agent agent) throws ValidationException {
        if(agent == null) {
            throw new ValidationException("Agent cannot be null");
        }

        validateEmail(agent.getEmail());
        validatePassword(agent.getPassword());
        validateSalary(agent.getSalary());
    }

    private void validateEmail(String email) {
        if(email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email cannot be null");
        }

        if(!email.contains("@") || !email.contains(".")) {
            throw new ValidationException("Email format not valid");
        }

        if (email.startsWith("@") || email.endsWith("@")) {
            throw new ValidationException("Email format is invalid");
        }
    }

    private void validatePassword(String password) {
        if(password == null || password.trim().isEmpty()) {
            throw new ValidationException("Password cannot be empty");
        }

        if(password.length() < 6) {
            throw new ValidationException("Password should contain more 6 characters");
        }
    }

    private void validateSalary(double salary) {
        if(salary <= 0) {
            throw new ValidationException("Salary cannot be less than 0");
        }
    }
}
