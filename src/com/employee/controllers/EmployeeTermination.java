package com.employee.controllers;

import com.employee.domain.Employee;
import com.employee.domain.Payroll;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeTermination implements Initializable {

    private ToggleGroup radioGroup;

    private String selected;
    @FXML
    private Button cancelButton;

    @FXML
    private Text errorprompt;

    @FXML
    private RadioButton idRadioButton;

    @FXML
    private TextField searchField;

    @FXML
    private Button submitButton;

    @FXML
    private Button terminateButton;

    @FXML
    private TextArea textAreaField;

    private Employee foundEmployee;

    @FXML
    private RadioButton usernameRadioButton;
    private EmployeeType type;

    private MainWindow window = MainWindow.getWindow();
    private Payroll payroll = Payroll.getPayroll();

    @FXML
    void onCancel(ActionEvent event) throws IOException {
        window.setStage("src/com/employee/UI/menuPage.fxml", "Boss Window");
    }

    @FXML
    void onSubmit(ActionEvent event) {
        Employee employee = null;
        textAreaField.setVisible(false);
        terminateButton.setVisible(false);
        String input = searchField.getText();
        if (input.equals("")) {
            setVisibiity(errorprompt, "Enter a Value");
            return;
        }
        if (type == EmployeeType.SALARIED) {
            if (!validateInput(input)) {
                setVisibiity(errorprompt, "Invalid input");
                return;
            } else {
                employee = payroll.getUser(Integer.parseInt(input));
                if (employee == null) {
                    setVisibiity(errorprompt, "Employeee not found");
                } else {
                    textAreaField.setText(employee.toString());
                    textAreaField.setVisible(true);
                    errorprompt.setVisible(false);
                }
            }
            return;
        }
        if (type == EmployeeType.HOURLY) {
            employee = payroll.getUser(input);
            if (employee == null) {
                setVisibiity(errorprompt, "Employeee not found");
            } else {
                textAreaField.setText(employee.toString());
                textAreaField.setVisible(true);
                errorprompt.setVisible(false);
            }
        }
        if (employee != null) {
            terminateButton.setVisible(true);
            foundEmployee = employee;
        }
    }

    private void setVisibiity(Text fullnameText, String s) {
        fullnameText.setVisible(true);
        fullnameText.setText(s);
    }

    @FXML
    void onTerminate(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Do you wanna fire this employee",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            // ... user chose YES
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "You have successfully fired the employee");
            payroll.terminateEmployee(foundEmployee.getEmpID());
            alert1.showAndWait();
            window.setStage("src/com/employee/UI/menuPage.fxml", "Login");
        } else {
            alert.close();
        }
    }

    @FXML
    void onIdRadio(ActionEvent event) {
        searchField.setPromptText("Enter employee id");
        if (type != null) {
            searchField.setVisible(true);
            submitButton.setVisible(true);
        }
    }

    public boolean validateInput(String num) {
        int intValue;
        try {
            intValue = Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
            return false;
        }
    }

    @FXML
    void onUserRadio(ActionEvent event) {
        searchField.setPromptText("Enter employee Username");
        if (type != null) {
            searchField.setVisible(true);
            submitButton.setVisible(true);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioGroup = new ToggleGroup();
        usernameRadioButton.setToggleGroup(radioGroup);
        idRadioButton.setToggleGroup(radioGroup);
        usernameRadioButton.selectedProperty().addListener((observableValue, aBoolean, selected) -> {
            if (selected) {
                type = EmployeeType.SALARIED;
                System.out.println(type);
            }
        });
        idRadioButton.selectedProperty().addListener((observableValue, aBoolean, selected) -> {
            if (selected) {
                type = EmployeeType.HOURLY;
            }
        });

    }
}
