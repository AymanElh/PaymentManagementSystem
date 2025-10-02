package com.paymentmanagement.model;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Payment implements Comparable<Payment> {
    private int id;
    private PaymentType paymentType;
    private double amount;
    private Date paymentDate;
    private boolean conditionValidation;
    private Agent agent;

    // Constructor for payment service
    public Payment(Agent agent, double amount, boolean conditionValidation, Date paymentDate) {
        this.agent = agent;
        this.amount = amount;
        this.conditionValidation = conditionValidation;
        this.paymentDate = paymentDate;
    }

    public Payment(Agent agent, double amount, boolean conditionValidation, Date paymentDate, PaymentType paymentType) {
        this.agent = agent;
        this.amount = amount;
        this.conditionValidation = conditionValidation;
        this.paymentDate = paymentDate;
        this.paymentType = paymentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isConditionValidation() {
        return conditionValidation;
    }

    public void setConditionValidation(boolean condition) {
        this.conditionValidation = condition;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getDateAsString() {
        return (new SimpleDateFormat("YYYY-MM-dd")).format(paymentDate);
    }

    @Override
    public int compareTo(Payment o) {
        return Double.compare(this.amount, o.amount);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", agent name=" + agent.getFirstName() + agent.getLastName() +
                ", paymentType=" + paymentType +
                ", amount=" + amount +
                ", payment_date=" + paymentDate +
                ", condition=" + conditionValidation +
                '}';
    }
}
