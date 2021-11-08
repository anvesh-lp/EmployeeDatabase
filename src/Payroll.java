import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Payroll {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String RESET = "\033[0m";  // Reset text colour
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String GREEN = "\033[0;32m";   // GREEN

    // Array list to store employee details

    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<Employee> terminated = new ArrayList<>();
    private Employee currentUser;
    private int currentId = -1;
    private static String MENU = "Payroll Menu\n\t1. Log In "
            + "\n\t2. Enter employees"
            + "\n\t3. List Employees"
            + "\n\t4. Update employees"
            + "\n\t5. Terminate employees"
            + "\n\t6. Pay employees \n\t0. Exit system" +
            "\nEnter your choice";


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
                        count++;
                        employees.add(emp);
                    } catch (EOFException e) {
                        break;
                    }
                }
                System.out.println(count);
                Employee.setNextID(count - 1);
                if (count == 0) {
                    throw new FileNotFoundException();
                }
//                Calling show menu function to show the user his choises
                showMenu();
            } else {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException | ClassNotFoundException e) {
//            Creating the boss if the user is logging the first time
            Scanner anve = new Scanner(System.in);
            System.out.println("Database is missing......!");
            System.out.println("Enter a login name to register");
            String username = anve.next();
            anve.nextLine();
            System.out.println("Enter your full name");
            String name = anve.nextLine();
            System.out.println("Enter your base salary");
            addToFile(anve, username, name);
            showMenu();
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

//    Function to add an employee to the file

    private Employee addToFile(Scanner anve, String username, String name) throws IOException {
        double salary = anve.nextDouble();
        Employee firstEmployee = new Hourly(username, salary, name);

        FileOutputStream writer = new FileOutputStream("employee.txt", true);
        ObjectOutputStream obj = new ObjectOutputStream(writer);
        obj.writeObject(firstEmployee);
        obj.flush();
        writer.close();
        employees.add(firstEmployee);
        return firstEmployee;
    }

    private void updateFile() {
        for(Employee emp:employees){
            FileOutputStream writer= null;
            ObjectOutputStream obj;
            try {
                writer = new FileOutputStream("employee.txt",false);
                 obj= new ObjectOutputStream(writer);
                obj.writeObject(emp);
                obj.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (writer!=null){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                        break;
                    case 1:
                        dologin();
                        break;
                    case 2:
                        System.out.println(currentId);
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
    private void payEmployee() {
        System.out.println("Functionality coming soon");

    }

    private void terminateEmployee() {
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
                    System.out.println(GREEN + "Employee has been successfully terminated" + RESET);
                }
            }

        } else {
            terminated.add(employees.remove(currentId));
            updateFile();
            System.out.println(YELLOW + "You have been successully terminated" + RESET);
            currentUser = null;
            currentId = -1;
        }
        System.out.println("Functionality coming soon");
    }

    private void updateEmployee() {
        if (currentId == 0) {
            System.out.println("Enter the loginname of the employee you want to edit ");
            Scanner anv = new Scanner(System.in);
            String name = anv.next();
            Employee emp = employees.stream().filter(employee -> employee.getUserName().equals(name)).findFirst().orElse(null);
            if (emp == null) {
                System.out.println(YELLOW + "Employee you are looking for is not in the database" + RESET);
            } else {
                System.out.println("enter the new full name of the employee " + emp.getEmpName());
                String fullname = anv.nextLine();
                anv.nextLine();
                System.out.println("Enter the updated salary of the employee");
                double salary = anv.nextDouble();
                emp.setBaseSalary(salary);
                emp.setEmpName(fullname);
                updateFile();
                System.out.println("Employee after updating the details \n " + emp);
            }
        }
    }

    private void listEmployees() {
        if (currentId == 0) {
            employees.forEach(System.out::println);
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
}

