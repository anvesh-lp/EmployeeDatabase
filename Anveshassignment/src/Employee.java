import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee {
    private final int empID;
    private String loginName;
    private double baseSalary;
    private String startDate;
    private String empName;

    private static int nextID=0;

    private static final SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
// Employee constructor
    public Employee(String userName, double baseSalary, String empName) {
        this.loginName = userName;
        this.baseSalary = baseSalary;
        this.empName = empName;
//        Formatting the date
        this.startDate= formatter.format(new Date());
        this.empID=nextID;
//        Updating the employee id each time a new employee object is  created.
        Employee.nextID++;
    }

    public String getEmpName() {
        return empName;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getUserName() {
        return loginName;
    }

    public int getEmpID() {
        return empID;
    }

//    ToString method to display the employee details
    @Override
    public String toString() {
        return String.format("%05d",empID) + "\t" + loginName  + "\t" + baseSalary + "\t" + startDate + "\t" + empName ;
    }
}
