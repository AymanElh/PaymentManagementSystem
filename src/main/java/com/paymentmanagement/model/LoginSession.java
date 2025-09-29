package com.paymentmanagement.model;

public class LoginSession {
    private Agent currentAgent;

    public LoginSession(Agent currentAgent) {
        this.currentAgent = currentAgent;
    }

    public String getUserName() {
        return currentAgent.getFirstName() + " " + currentAgent.getLastName();
    }
}
