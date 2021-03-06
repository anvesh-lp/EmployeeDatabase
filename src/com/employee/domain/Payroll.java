package com.employee.domain;

import com.employee.controllers.EmployeeType;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Payroll {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String RESET = "\033[0m";  // Reset text colour
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String GREEN = "\033[0;32m";   // GREEN


    // Array list to store employee details
    private static Payroll payroll;
    private static String MENU = "com.employee.domain.Payroll Menu\n\t1. Log In "
            + "\n\t2. Enter employees"
            + "\n\t3. List Employees"
            + "\n\t4. Update employees"
            + "\n\t5. Terminate employees"
            + "\n\t6. Pay employees \n\t0. Exit system" +
            "\nEnter your choice";
    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<Employee> terminated = new ArrayList<>();
    private PrintWriter writer = null;
    private Employee currentUser;
    private int currentId = -1;


    //    Constructor to create the boss and intitalize the employees
    public Payroll() {
        try {
//            To open the file inside the constructor
            File report = new File("payroll.txt");
            writer = new PrintWriter(report);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }

    }

    public static Payroll getPayroll() {
        if (payroll == null) {
            payroll = new Payroll();
            return payroll;
        }
        return payroll;
    }

    public static boolean getNewPassword(String fpassword, String secondpassword) throws NoSuchAlgorithmException {
        MessageDigest digest1 = MessageDigest.getInstance("SHA-256");
        digest1.update(fpassword.getBytes());
        byte[] byteData = digest1.digest();
        StringBuilder encrypted1 = new StringBuilder();
        for (byte datum : byteData) {
            String hex = Integer.toHexString(0xff & datum);
            encrypted1.append(hex);
        }
        MessageDigest digest2 = MessageDigest.getInstance("SHA-256");
        digest2.update(secondpassword.getBytes());
        byte[] byteData1 = digest2.digest();
        StringBuilder encrypted2 = new StringBuilder();
        for (byte byteDatum : byteData1) {
            String hex1 = Integer.toHexString(0xff & byteDatum);
            encrypted2.append(hex1);
        }
        if (encrypted1.toString().equals(encrypted2.toString())) {
            return true;
        }
        return false;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public ArrayList<Employee> getTerminated() {
        return terminated;
    }

    public Employee getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Employee currentUser) {
        this.currentUser = currentUser;
    }

//    Function to add an employee to the file

    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

    //    Do menu to prompt user the choises
    public void doMenu() throws IOException {
        Scanner sc = null;
        try {
//            creating and opening the file
            File myObj = new File("employee.txt");
            if (!myObj.createNewFile()) {
                FileInputStream fs = new FileInputStream(myObj);
                int count = 0;
                while (true) {
                    Employee emp = null;
                    try {
                        ObjectInputStream objInput = new ObjectInputStream(fs);
                        emp = (Employee) objInput.readObject();
//                        System.out.println(emp);
                        count++;
                        employees.add(emp);
                    } catch (EOFException e) {
                        break;
                    }
                }
                fs.close();

                if (count != 0) {
                    Employee.setNextID(count);
                }
//                Calling show menu function to show the user his choises
//                showMenu();
            }
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
//                Closing the files
                if (sc != null) {
                    sc.close();
                }
            } catch (Exception e) {
                System.out.println();
            }
        }
    }

    private void addToFile(Employee employee) throws IOException {
        FileOutputStream writer = new FileOutputStream("employee.txt", true);
        ObjectOutputStream obj = new ObjectOutputStream(writer);
        obj.writeObject(employee);
        obj.flush();
        writer.close();
        employees.add(employee);
    }

    private Employee addToFile(Scanner anve, String username, String name) throws IOException {
        double salary = anve.nextDouble();
        System.out.println("Please choose payroll type \n\t1.com.employee.domain.Salaried\n\t2.com.employee.domain.Hourly");
        int num = -1;
        while (true) {
            try {
                num = anve.nextInt();
                if (num < 1 || num > 2) {
                    throw new InputMismatchException();
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println(ANSI_RED + "Invalid Input please choose either 1 or 2" + RESET);
            }
        }
        Employee firstEmployee = null;
        if (num == 2) {
            firstEmployee = new Hourly(username, salary, name);
        } else {
            firstEmployee = new Salaried(username, salary, name);
        }
        FileOutputStream writer = new FileOutputStream("employee.txt", true);
        ObjectOutputStream obj = new ObjectOutputStream(writer);
        obj.writeObject(firstEmployee);
        obj.flush();
        writer.close();
        employees.add(firstEmployee);
        return firstEmployee;
    }

    private void updateFile() throws IOException {
        FileOutputStream writer = null;
        try {
            ObjectOutputStream obj = null;
            writer = new FileOutputStream("employee.txt");
            for (Employee emp : employees) {
                obj = new ObjectOutputStream(writer);
                obj.writeObject(emp);
                obj.flush();
            }
            if (obj != null)
                obj.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //    Show menu function to show the use his choises
    private void showMenu() throws IOException {
        boolean run = true;
//        A while loop to run until the user enters o
        while (run) {
            System.out.println(MENU);
            int choice;
            try {
                Scanner in = new Scanner(System.in);
                choice = in.nextInt();
                switch (choice) {
                    case 0:
                        run = false;
                        if (terminated.size() != 0) {
                            System.out.println("Terminated employees");
                            terminated.forEach(System.out::println);
                        }
                        break;
                    case 1:
                        dologin();
                        break;
                    case 2:
//                        System.out.println(currentId);
                        if (currentId == -1) {
                            System.out.println("You must be logged in to add employee");
                            break;
                        } else if (currentId != 0) {
                            System.out.println("only Boss can add employee");
                            break;
                        }
                        newEmployee();
                        break;
                    case 3:
                        listEmployees();
                        break;
                    case 4:
                        updateEmployee();
                        break;
                    case 5:
                        terminateEmployee();
                        break;
                    case 6:
                        payEmployee();
                        break;
                    default:
                        System.out.println(ANSI_RED + "invalid option. Please choose one from below\n\n" + RESET);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(ANSI_RED + "Invalid input...please try again" + RESET);
            }
        }
    }

    /**
     * method to be implemented
     */
    public String runPayroll() {
        StringBuilder s = new StringBuilder();
        for (Employee emp : employees) {
            if (emp.getEmpID() == 0) {
                continue;
            }
            double pay = emp.getPay();
            double updated = 0;
            int hours = emp.getExtraHours();
            System.out.println(hours);
            if (emp instanceof Hourly) {
                updated = hours * emp.getBaseSalary() * 1.5;
                pay += updated;
            } else {
                if (hours >= 100) {
                    updated = (10 * pay) / 100;
                    pay += updated;
                }
            }
            s.append(String.format("%.2f (overtime Hours %s)", pay, hours)).append(String.format("\t\t%05d", emp.getEmpID())).append(String.format("\t\t%s", emp.getEmpName()));
            s.append("\n");
        }
        String sp = "com.employee.domain.Payroll Report  \t\t" + new Date();
        String so = "Pay" + "\t\t" + "ID" + "\t\t" + "Name";
        System.out.println(sp);
        System.out.println(so);
        if (writer != null) {
            writer.write(sp);
            writer.write("\n");
            writer.write(so);
            writer.write("\n");
            writer.write(String.valueOf(s));
            writer.flush();
            writer.close();
        }
        return s.toString();
    }

    private void payEmployee() {
//        System.out.println("Functionality coming soon");
        Scanner anv = new Scanner(System.in);

        StringBuilder s = new StringBuilder();
        for (Employee emp : employees) {
            if (emp.getEmpID() == 0) {
                continue;
            }
            double pay = emp.getPay();
            double updated = 0;
            System.out.println("Any OverTime Hours? 1.yes 2.No \tenter a number");
            int num = anv.nextInt();
            int h = 0;
            if (num == 1) {
                System.out.println("enter no of overtimeHours");
                int hours;
                while (true) {
                    try {
                        hours = anv.nextInt();
                        if (hours <= 0) {
                            throw new InputMismatchException();
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter valid hours greater than zero");
                    }
                }
                if (emp instanceof Hourly) {
                    updated = hours * emp.getBaseSalary() * 1.5;
                    pay += updated;
                } else {
                    if (hours >= 100) {
                        updated = (10 * pay) / 100;
                        pay += updated;
                    }
                }
                h = hours;
            }
            s.append(String.format("%.2f (overtime Hours %s)", pay, h)).append(String.format("\t\t%05d", emp.getEmpID())).append(String.format("\t\t%s", emp.getEmpName()));
            s.append("\n");
        }
        String sp = "com.employee.domain.Payroll Report  \t\t" + new Date();
        String so = "Pay" + "\t\t" + "ID" + "\t\t" + "Name";
        System.out.println(sp);
        System.out.println(so);
        if (writer != null) {
            writer.write(sp);
            writer.write("\n");
            writer.write(so);
            writer.write("\n");
            writer.write(String.valueOf(s));
            writer.flush();
            writer.close();
        }
        System.out.println(s);
    }

    public void terminateEmployee(int id) throws IOException {
//        if (currentId == id) {
        Employee emp = employees.stream().filter(employee -> employee.getEmpID() == id).findFirst().orElse(null);
        if (emp == null) {
            System.out.println("Employee not found Termination method");
        } else {
            if (employees.removeIf(employee -> employee.getEmpID() == id)) {
                terminated.add(emp);
                updateFile();
            }
        }
    }

    private void terminateEmployee() throws IOException {
        if (currentId == 0) {
            System.out.println(" Enter the employee id you wish to terminate ");
            Scanner anv = new Scanner(System.in);
            int id = anv.nextInt();
            Employee emp = employees.stream().filter(employee -> employee.getEmpID() == id).findFirst().orElse(null);
            if (emp == null) {
                System.out.println(YELLOW + "cant find employee you are looking for...please try again " + RESET);
            } else {
                if (employees.removeIf(employee -> employee.getEmpID() == id)) {
                    terminated.add(emp);
                    updateFile();
                    System.out.println(GREEN + "com.employee.domain.Employee has been successfully terminated" + RESET);
                }
            }
        } else {
            employees.removeIf(employee -> employee.getEmpID() == currentId);
            terminated.add(currentUser);
            updateFile();
            System.out.println(YELLOW + "You have been successully terminated" + RESET);
            currentUser = null;
            currentId = -1;
        }
        System.out.println("Functionality coming soon");
    }

    public void updateEmployee(String username, String updateName, double updatedSalary) throws IOException {
        Employee emp = employees.stream().filter(employee -> employee.getUserName().equals(username)).findFirst().orElse(null);
        if (emp == null) {
            System.out.println(YELLOW + "com.employee.domain.Employee you are looking for is not in the database" + RESET);
        } else {
            emp.setBaseSalary(updatedSalary);
            emp.setEmpName(updateName);
            System.out.println("com.employee.domain.Employee after updating the details \n " + emp);
            updateFile();
        }
    }

    private void updateEmployee() throws IOException {
        if (currentId == 0) {
            System.out.println("Enter the loginname of the employee you want to edit ");
            Scanner anv = new Scanner(System.in);
            String name = anv.next();
            anv.nextLine();
            Employee emp = employees.stream().filter(employee -> employee.getUserName().equals(name)).findFirst().orElse(null);
            if (emp == null) {
                System.out.println(YELLOW + "com.employee.domain.Employee you are looking for is not in the database" + RESET);
            } else {
                System.out.println("enter the new full name of the employee (previous name)" + emp.getEmpName());
                String fullname = anv.nextLine();
                anv.nextLine();
                System.out.println(fullname);
                System.out.println("Enter the updated salary of the employee (previous salary) " + emp.getBaseSalary());
                double salary = anv.nextDouble();
                emp.setBaseSalary(salary);
                emp.setEmpName(fullname);
                System.out.println("com.employee.domain.Employee after updating the details \n " + emp);
                updateFile();
            }
        } else {
            System.out.println("Only boss could update");
        }
    }

    private void listEmployees() {
        if (currentId == 0) {
            employees.forEach(employee -> {
                if (employee.getEmpID() != currentId) {
                    System.out.println(employee);
                }
            });
        } else {
            System.out.println(currentUser);
        }
    }

    //    Creating a new employeee funtion
    private void newEmployee() throws IOException {
        System.out.println("************************ new employee ****************************");
        System.out.println("Enter the login name of the newEmployee");
        Scanner anv = new Scanner(System.in);
        String username = anv.next();
//      Iterating through the employee list to check if the login name is already present
        if (employees.stream().anyMatch(employee -> employee.getUserName().equals(username))) {
            System.out.println(YELLOW + "login name Already Exists..Please try again" + RESET);
            return;
        }
        System.out.println("Enter the Full name of the employee");
        anv.nextLine();
        String name = anv.nextLine();
        System.out.println("Enter the salary of the employee");
//      Adding the employee to the database
        Employee emp = addToFile(anv, username, name);
        System.out.println(GREEN + "New employee created and added to data base" + RESET);
        System.out.println(emp);
    }

    public void createNewEmployee(Employee newemployee) throws IOException {
        addToFile(newemployee);
    }

    public boolean checkuserNameUnique(String username) {
        return employees.stream().anyMatch(employee -> employee.getUserName().equals(username));
    }

    //    Login fucntion for logging the user
    private void dologin() {
        Scanner anv = new Scanner(System.in);
        System.out.println("enter your login name");
        String name = anv.next();
//        Checking of the employee is in the array list or not
        Employee found = employees.stream().filter(employee -> employee.getUserName().equals(name)).findFirst().orElse(null);
        if (found == null) {
            System.out.println(YELLOW + "Username not found..Please try again" + RESET);
            return;
        }
        System.out.println("Successfully logged in...");
        System.out.println("hello " + found.getEmpName());
//        Setting the current user to found employee
        currentUser = found;
        currentId = found.getEmpID();
    }

    //    Check if username already exists in the database.
    public boolean checkUnique(String username) {
        return employees.stream().anyMatch(employee -> employee.getUserName().equals(username));
    }

    public void createNewEmployee(String user, double parseDouble, String fullname, EmployeeType type, String pass1, int hours, int mainHours) throws IOException {
        Employee emp = null;
        if (type == EmployeeType.HOURLY) {
            emp = new Hourly(user, parseDouble, fullname);
            emp.setPassword(pass1);
            Hourly h = (Hourly) emp;
            h.setHours(mainHours);
//            emp=h;
        } else if (type == EmployeeType.SALARIED) {
            emp = new Salaried(user, parseDouble, fullname);
            emp.setPassword(pass1);
        }
        if (emp != null && emp.getEmpID() == 0) {
            currentId = emp.empID;
            currentUser = emp;
        }
        if (emp != null) {
            emp.setExtraHours(hours);
        }
        addToFile(emp);
    }

    public Employee getUser(String username) {
        return employees.stream().filter(employee -> employee.getUserName().equals(username)).findAny().orElse(null);
    }

    public Employee getUser(int id) {
        return employees.stream().filter(employee -> employee.getEmpID() == id).findAny().orElse(null);
    }
}

