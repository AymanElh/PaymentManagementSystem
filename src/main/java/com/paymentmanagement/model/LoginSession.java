package com.paymentmanagement.model;

public class LoginSession {
    private Agent currentLoggedAgent;

    public LoginSession(Agent currentLoggedAgent) {
        this.currentLoggedAgent = currentLoggedAgent;
    }

    public Agent getCurrentLoggedAgent() {
        return currentLoggedAgent;
    }

    public void setCurrentLoggedAgent(Agent currentLoggedAgent) {
        this.currentLoggedAgent = currentLoggedAgent;
    }

    public String getUserName() {
        return  currentLoggedAgent.getFirstName() + " " + currentLoggedAgent.getLastName();
    }
}
