public class Salaried extends Employee{
    public Salaried(String userName, double baseSalary, String empName) {
        super(userName, baseSalary, empName);
    }

    @Override
    public double getPay() {
        return baseSalary/24;
    }
}
