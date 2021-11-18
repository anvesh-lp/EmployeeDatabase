package controllers;

import domain.Payroll;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;


public class MainWindowController {
    public static MainWindow window;
    public Payroll payrol = Payroll.getPayroll();

    //    @FXML
    public void enterButton(ActionEvent event) throws IOException {
        System.out.println("Button clicked");
        payrol.doMenu();
        if (payrol.getEmployees().size() == 0) {
//          Show Registration page
            getMainWindowInstance().setStage("../UI/Registration.fxml", "");
        } else {
//          Show Login Page
            getMainWindowInstance().setStage("../UI/login.fxml", "Login");
        }
    }

    public static MainWindow getMainWindowInstance() {
        if (window == null) {
            window = new MainWindow();
            return window;
        }
        return window;
    }
}
