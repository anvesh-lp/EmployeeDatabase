package domain;

import java.io.IOException;

/**
 * Start of the program
 */

public class Main {

    public static void main(String[] args) {
        System.out.println("---------- Welcome to domain.Employee database -> Anvesh ----------");
        try {
//            Creating the payroll object
            Payroll initiatingPayroll=new Payroll();
            initiatingPayroll.doMenu();
//            Catching the ioException if it rises
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
