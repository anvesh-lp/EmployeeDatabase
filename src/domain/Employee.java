package domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Employee implements Serializable {
    protected final int empID;
    protected String loginName;
    protected double baseSalary;
    protected String startDate;
    protected String empName;
    protected String password;
    protected int extraHours=0;

    private static int nextID = 0;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public int getExtraHours() {
        return extraHours;
    }

    public void setExtraHours(int extraHours) {
        this.extraHours = extraHours;
    }

    // domain.Employee constructor
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void setNextID(int nextID) {
        Employee.nextID = nextID;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getStartDate() {
        return startDate;
    }

    public static int getNextID() {
        return nextID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
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
        return String.format("%05d", empID) + "\t" + loginName + "\t" + baseSalary + "\t" + startDate + "\t" + empName;
    }
}
