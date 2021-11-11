import java.util.Date;
import java.util.Scanner;

public class Hourly extends Employee{
    public Hourly(String userName, double baseSalary, String empName) {
        super(userName, baseSalary, empName);
    }

    @Override
    public double getPay() {
        System.out.println("Enter the number of hours for this pay period (Half of the month)  for employee "+this.empName);
        Scanner anv= new Scanner(System.in);
        int hours=anv.nextInt();
        return baseSalary*hours;
    }
}
