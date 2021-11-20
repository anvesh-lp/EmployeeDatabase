package com.employee.controllers;

import com.employee.domain.Payroll;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;


public class MainWindowController {
    public static MainWindow window;
    public Payroll payrol = Payroll.getPayroll();

    public static MainWindow getMainWindowInstance() {
        if (window == null) {
            window = new MainWindow();
            return window;
        }
        return window;
    }

    @FXML
    public void enterButton(ActionEvent event) throws IOException {
        System.out.println("Button clicked");
        payrol.doMenu();
        if (payrol.getEmployees().size() == 0) {
//          Show Registration page
            getMainWindowInstance().setStage("src/com/employee/UI/Registration.fxml", "");
        } else {
//          Show Login Page
            getMainWindowInstance().setStage("src/com/employee/UI/Login.fxml", "Login");
        }
    }
}
