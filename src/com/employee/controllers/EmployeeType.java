package com.employee.controllers;

public enum EmployeeType {
    SALARIED("S"), HOURLY("H");

    private final String type;

    EmployeeType(String s) {
        this.type = s;
    }

    public String getType() {
        return type;
    }
}
