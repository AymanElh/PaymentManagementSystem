package com.paymentmanagement.view;

import com.paymentmanagement.model.AgentType;

public class MenuItem {
    private String text;
    private int id;

    public MenuItem(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + ". " + text;
    }
}
