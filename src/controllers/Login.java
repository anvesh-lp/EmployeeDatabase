package controllers;

import domain.Employee;
import domain.Payroll;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Login {

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private Text passwordprompt;

    @FXML
    private TextField username;

    @FXML
    private Text usernamePrompt;

    private Payroll payroll = Payroll.getPayroll();
    private MainWindow window = new MainWindow();

    @FXML
    void onSubmitHandler(ActionEvent event) throws NoSuchAlgorithmException, IOException {
        Employee emp = null;
        if (username.getText().equals("")) {
            setVisibe(usernamePrompt, "User name required");
        } else {
            usernamePrompt.setVisible(false);
            emp = payroll.getUser(username.getText());
        }
        if (password.getText().equals("")) {
            setVisibe(passwordprompt, "Field required");
        } else {
            passwordprompt.setVisible(false);
        }
        if (emp==null){
            setVisibe(usernamePrompt,"Username Not found ");
        }
        if (emp != null && Payroll.getNewPassword(password.getText(), emp.getPassword())) {
            if (emp.getEmpID() == 0) {
                window.setStage("../UI/menuPage.fxml", "Employee");
            } else {
                payroll.setCurrentId(emp.getEmpID());
                payroll.setCurrentUser(emp);
                window.setStage("../UI/employeeDetails.fxml", "EmployeeDetails");
            }
        } else {
            setVisibe(passwordprompt, "Password Incorrect");
        }
    }

    public void setVisibe(Text text, String message) {
        text.setVisible(true);
        text.setText(message);
    }

}
