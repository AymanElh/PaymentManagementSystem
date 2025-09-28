package com.paymentmanagement.view;

public class MenuItem {
    private String text;
    private int value;

    public MenuItem(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
