package com.employee.domain;

public class Hourly extends Employee {
    private int hours;

    public Hourly(String userName, double baseSalary, String empName) {
        super(userName, baseSalary, empName);
    }

    @Override
    public double getPay() {
        return baseSalary * hours;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
