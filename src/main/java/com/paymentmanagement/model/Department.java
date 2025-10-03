package com.paymentmanagement.model;

public class Department {
    private int id;
    private String name;
    private String description;

    public Department(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Department(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = generateSimpleId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int generateSimpleId() {
        System.out.println(this.name.hashCode());
        return Math.abs(this.name.hashCode()) % 10000;
    }


    @Override
    public String toString() {
        return "Department{" +
                ", id=" + id +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
