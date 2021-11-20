package com.employee.controllers;

import com.employee.domain.Employee;
import com.employee.domain.Payroll;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerMenu implements Initializable {

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

    private MainWindow window = MainWindow.getWindow();
    private Payroll payroll = Payroll.getPayroll();

    @FXML
    void createNewEmployee(ActionEvent event) throws IOException {
        window.setStage("src/com/employee/UI/Registration.fxml", "New Employee");
    }

    @FXML
    void logoutHandler(ActionEvent event) throws IOException {
        window.setStage("src/com/employee/UI/Login.fxml", "Login");
    }

    @FXML
    void runPayroll(ActionEvent event) throws IOException {
        window.setStage("src/com/employee/UI/payrollEmployee.fxml", "Payroll");
    }

    @FXML
    void terminateEmployee(ActionEvent event) throws IOException {
        window.setStage("src/com/employee/UI/employeeTermination.fxml", "Terminate employee");
    }

    @FXML
    void updateEmployee(ActionEvent event) throws IOException {
        window.setStage("src/com/employee/UI/updateEmployee.fxml", "Update Employee");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Employee> employeeArrayList = payroll.getEmployees();
        if (employeeArrayList.size() == 0) {
            textAreaField.appendText("No Employees in the Database. Click on add to create new employee");
        } else {
            employeeArrayList.forEach(employee -> textAreaField.appendText(employee.getEmpID() == 0 ? employee+ " \n" : employee.toString() + " \n"));
        }
    }
}
