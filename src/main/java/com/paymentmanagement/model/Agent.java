package com.paymentmanagement.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Agent extends User {
    private int id;
    private AgentType agentType;
    private boolean isActive;
    private Date startDate;
    private Department department;
    private List<Payment> payments;

    // Constructors

    public Agent(String firstName, String lastName, String email, String password, String phone, AgentType agentType, Department department) {
        super(firstName, lastName, email, password, phone);
        this.id = generateAgentId();
        this.agentType = agentType;
        this.department = department;
    }

    public Agent(String firstName, String lastName, String email, String password, String phone, AgentType agentType, Department department, List<Payment> payments) {
        super(firstName, lastName, email, password, phone);
        this.id = generateAgentId();
        this.agentType = agentType;
        this.department = department;
        this.payments = payments;
    }

    // Constructor for creating new agents (generates IDs)
    public Agent(String firstName, String lastName, String email, String password, String phone, AgentType agentType, boolean isActive, Date startDate) {
        super(firstName, lastName, email, password, phone);
        this.id = generateAgentId();
        this.agentType = agentType;
        this.isActive = isActive;
        this.startDate = startDate;
    }

    public Agent(int userId, int agentId, String firstName, String lastName, String email, String password, String phone,
                 AgentType agentType, boolean isActive, Date startDate, Department department) {
        super(userId, firstName, lastName, email, password, phone);
        this.id = agentId;
        this.agentType = agentType;
        this.isActive = isActive;
        this.startDate = startDate;
        this.department = department;
    }


    // Getters and setters

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public AgentType getAgentType() {
        return agentType;
    }

    public void setAgentType(AgentType agentType) {
        this.agentType = agentType;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getStartDateAsString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        return simpleDateFormat.format(startDate);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getUserId() {
        return super.getId();
    }
    public void setUserId(int id) {
        super.setId(id);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int generateAgentId() {
        int baseId = (int) (System.currentTimeMillis() % 100000);
        int typeMultiplier = agentType == AgentType.MANAGER ? 1000 : 100;
        return baseId + typeMultiplier;
    }


    // Override methods

    @Override
    public String toString() {

        return String.format("""
                        -- Agent info -- 
                        \t Id: %d 
                        \t First Name: %s 
                        \t LastName: %s 
                        \t Email: %s 
                        \t Phone: %s 
                        \t Department: %s 
                        \t Type: %s  
                        \t Start date %s 
                        """,
                getId(), getFirstName(), getLastName(), getEmail(), getPhone(), department.getName(), getAgentType().name(), getStartDateAsString());
    }
}
