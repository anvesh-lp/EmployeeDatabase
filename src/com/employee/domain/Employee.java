package com.employee.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Employee implements Serializable {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private static int nextID = 0;
    protected final int empID;
    protected String loginName;
    protected double baseSalary;
    protected String startDate;
    protected String empName;
    protected String password;
    protected int extraHours = 0;

    // com.employee.domain.Employee constructor
    public Employee(String userName, double baseSalary, String empName) {
        this.loginName = userName;
        this.baseSalary = baseSalary;
        this.empName = empName;
//        Formatting the date
        this.startDate = formatter.format(new Date());
        this.empID = nextID;
//        Updating the employee id each time a new employee object is  created.
        Employee.nextID++;
    }

    public static int getNextID() {
        return nextID;
    }

    public static void setNextID(int nextID) {
        Employee.nextID = nextID;
    }

    public int getExtraHours() {
        return extraHours;
    }

    public void setExtraHours(int extraHours) {
        this.extraHours = extraHours;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getUserName() {
        return loginName;
    }

    public int getEmpID() {
        return empID;
    }

    public abstract double getPay();

    //    ToString method to display the employee details
    @Override
    public String toString() {
        return String.format("%05d", empID) + "\t\t" + loginName + "\t\t" + baseSalary + "\t\t" + startDate + "\t\t" + empName;
    }
}
