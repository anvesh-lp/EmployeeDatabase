package controllers;

import domain.Main;
import domain.Payroll;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class ManagerMenu {

    @FXML
    private Button createemployeeButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button payrollButton;

    @FXML
    private Button terminateButton;

    @FXML
    private TextArea textAreaField;

    @FXML
    private Button updateEmployeeButton;

    private MainWindow window=new MainWindow();
    private Payroll payroll=Payroll.getPayroll();

    @FXML
    void createNewEmployee(ActionEvent event) throws IOException {
        window.setStage("../UI/Registration.fxml","New Employee");
    }

    @FXML
    void logoutHandler(ActionEvent event) throws IOException {
        window.setStage("../UI/Login.fxml","Login");
    }

    @FXML
    void runPayroll(ActionEvent event) {

    }

    @FXML
    void terminateEmployee(ActionEvent event) {

    }

    @FXML
    void updateEmployee(ActionEvent event) {

    }

}
