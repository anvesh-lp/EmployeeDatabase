package controllers;

import domain.Employee;
import domain.Payroll;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateEmployee implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private Label salaryLabel;

    @FXML
    private Label usernameLabel;

    private Employee foundEmployee;


    private ToggleGroup radioGroup;
    private EmployeeType type;


    @FXML
    private Text errorprompt;

    @FXML
    private RadioButton idRadioButton;

    @FXML
    private TextField salaryInput;

    @FXML
    private TextField searchField;

    @FXML
    private Button submitButton;

    @FXML
    private Button updateButton;

    @FXML
    private TextField usernameInput;

    @FXML
    private RadioButton usernameRadioButton;

    private Payroll payroll=Payroll.getPayroll();
    private MainWindow window=MainWindow.getWindow();

    @FXML
    void onCancel(ActionEvent event) throws IOException {
        window.setStage("../UI/menuPage.fxml","Boss Window");
    }

    public boolean validateInput(String num) {
        int intValue;
        num=num.split("[.]")[0];
        if (num==null){
            return false;
        }
        try {
            intValue = Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
            return false;
        }
    }

    private void setVisibiity(Text fullnameText, String s) {
        fullnameText.setVisible(true);
        fullnameText.setText(s);
    }

    @FXML
    void onIdRadio(ActionEvent event) {
        searchField.setPromptText("Enter employee id");
        if (type != null) {
            searchField.setVisible(true);
            submitButton.setVisible(true);
        }
    }

    @FXML
    void onSubmit(ActionEvent event) {
        Employee employee=null;
        showVisible(false);
        submitButton.setVisible(true);
        String input=searchField.getText();
        if (input.equals("")){
            setVisibiity(errorprompt,"Enter a Value");
            return;
        }
        if (type==EmployeeType.SALARIED){
            if (!validateInput(input)) {
                setVisibiity(errorprompt, "Invalid input");
                return;
            } else {
                employee= payroll.getUser(Integer.parseInt(input));
                if (employee==null){
                    setVisibiity(errorprompt,"Employeee not found");
                }else {
                    showVisible(true);
                    usernameInput.setText(employee.getEmpName());
                    System.out.println((""+employee.getBaseSalary()).split("[.]")[0]);
                    salaryInput.setText((""+employee.getBaseSalary()).split("[.]")[0]);
                    errorprompt.setVisible(false);
                }
            }
//            return;
        }
        if (type==EmployeeType.HOURLY){
            employee= payroll.getUser(input);
            if (employee==null){
                setVisibiity(errorprompt,"Employeee not found");
            }else {
                showVisible(true);
                usernameInput.setText(employee.getEmpName());
                salaryInput.setText((""+employee.getBaseSalary()).split("[.]")[0]);
                errorprompt.setVisible(false);
            }
        }
        if (employee!=null){
            updateButton.setVisible(true);
            foundEmployee=employee;
        }
    }

    @FXML
    void onUpdate(ActionEvent event) throws IOException {
        String userName=usernameInput.getText();
        String salary=salaryInput.getText();
        if (userName.equals("")){
            setVisibiity(errorprompt,"Username is required");
            return;
        }
        if (salary.equals("")){
            setVisibiity(errorprompt,"Salary is required");
            return;
        }else if (!validateInput(salary)){
            setVisibiity(errorprompt,"Invalid input");
            return;
        }
        if (validateInput(salary)){
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Employee details Successfully updated");
            payroll.updateEmployee(foundEmployee.getUserName(),userName,Double.parseDouble(salary));
            alert1.showAndWait();
            window.setStage("../UI/menuPage.fxml", "Boss Menu");
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

    public void showVisible(Boolean choise){
        usernameLabel.setVisible(choise);
        salaryLabel.setVisible(choise);
        salaryInput.setVisible(choise);
        usernameInput.setVisible(choise);
        updateButton.setVisible(choise);
    }
}
