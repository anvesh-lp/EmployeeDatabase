import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Payroll {
// Array list to store employee details
    private ArrayList<Employee> employees = new ArrayList<>();
    private Employee currentUser;
    private int currentId;
    private static String MENU = "Payroll Menu\n\t1. Log In "
            + "\n\t2. Enter employees"
            + "\n\t3. List Employees"
            + "\n\t4. List employees"
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
                sc = new Scanner(myObj);
                int count = 0;
                while (sc.hasNext()) {
//                    Reading from the file iof its already present
                    String[] empdata = sc.nextLine().split("\t");
                    Employee temp = new Employee(empdata[1], Double.parseDouble(empdata[2]), empdata[4]);
                    employees.add(temp);
                    count++;
                }
                if (count == 0) {
                    throw new FileNotFoundException();
                }
//                Calling show menu function to show the user his choises
                showMenu();
            } else {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
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
        Employee firstEmployee = new Employee(username, salary, name);
        FileOutputStream writer = new FileOutputStream("employee.txt",true);
        BufferedWriter bf=new BufferedWriter(new OutputStreamWriter(writer));
        bf.write(String.valueOf(firstEmployee));
        bf.newLine();
        bf.close();
        writer.close();
        employees.add(firstEmployee);
        return firstEmployee;
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
                        System.out.println("invalid option. Please choose one from below");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input...please try again");
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
        System.out.println("Functionality coming soon");
    }

    private void updateEmployee() {
        System.out.println("Functionality coming soon");
    }

    private void listEmployees() {
        System.out.println("Functionality coming soon");
    }

//    Creating a new employeee funtion
    private void newEmployee() throws IOException {
        System.out.println("************************ new employee ****************************");
        System.out.println("Enter the login name of the newEmployee");
        Scanner anv = new Scanner(System.in);
        String username = anv.next();
//        Iterating through the employee list to check if the login name is already present
        if (employees.stream().anyMatch(employee -> employee.getUserName().equals(username))) {
            System.out.println("login name Already Exists..Please try again");
            return;
        }
        System.out.println("Enter the Full name of the employee");
        anv.nextLine();
        String name = anv.nextLine();
        System.out.println("Enter the salary of the employee");
//        Adding the employee to the database
        Employee emp=addToFile(anv, username, name);
        System.out.println("New employee created and added to data base");
        System.out.println(emp);
    }

//    Login fucntion for logging the user
    private void dologin() {
        Scanner anv = new Scanner(System.in);
        System.out.println("enter your login name");
        String name = anv.next();
//        Checking of the employee is in the array list or not
        Employee found = employees.stream().filter(employee -> employee.getUserName().equals(name)).findAny().orElse(null);
        if (found == null) {

            System.out.println("Username not found..Please try again");
            return;
        }
        System.out.println("Successfully logged in...");
        System.out.println("hello " + found.getEmpName());
//        Setting the current user to found employee
        currentUser = found;
        currentId = found.getEmpID();
    }
}