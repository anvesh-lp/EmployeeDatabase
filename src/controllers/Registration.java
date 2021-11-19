package controllers;

import domain.Employee;
import domain.Hourly;
import domain.Payroll;
import domain.Salaried;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;


public class Registration implements Initializable {

    private final Payroll payroll = Payroll.getPayroll();
    private final MainWindow window = MainWindow.getWindow();


    @FXML
    private Text fullnameText;


    private ToggleGroup radioGroup;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password1;

    @FXML
    private CheckBox hourscheckBox;

    @FXML
    private TextField hoursTextField;

    @FXML
    private Text errorPromptCheckbox;

    @FXML
    private TextField hTextField;

    @FXML
    private PasswordField password2;

    @FXML
    private Text passwordText;

    @FXML
    private Text radioButtonText;

    @FXML
    private RadioButton radiobutton1;

    @FXML
    private RadioButton radiobutton2;

    @FXML
    private TextField salary;

    @FXML
    private Text salaryText;

    @FXML
    private Button submit;

    @FXML
    private TextField username;

    @FXML
    private Text usernameText;

    private String selected;
    private EmployeeType type;

    @FXML
    void checkSalary(MouseEvent event) {

    }

    public void setUpToggleButtons() {
    }

    @FXML
    void onHourlySelected(ActionEvent event) {
        if (radiobutton2.isSelected()){
            hTextField.setVisible(true);
        }else {
            hTextField.setVisible(false);
        }
    }
    @FXML
    void onSalarySelected(ActionEvent event) {

            hTextField.setVisible(false);

    }

    @FXML
    void onChecking(ActionEvent event) {
        if (hourscheckBox.isSelected()) {
            hoursTextField.setVisible(true);
        } else {
            hoursTextField.setVisible(false);
            errorPromptCheckbox.setVisible(false);
        }
    }

    @FXML
    void onSubmit(ActionEvent event) throws IOException, NoSuchAlgorithmException {
        String fullname = name.getText();
        String user = username.getText();
        String sal = salary.getText();
        String pass1 = password1.getText();
        String pass2 = password2.getText();
        if (fullname.equals("")) {
            setVisibiity(fullnameText, "Field Required");
        } else {
            fullnameText.setVisible(false);
        }
        if (Objects.equals(user, "")) {
            setVisibiity(usernameText, "Field Required");
        } else if (payroll.checkUnique(user)) {
            setVisibiity(usernameText, "UserName Already Exists");
        } else {
            usernameText.setVisible(false);
        }
        if (sal.equals("")) {
            setVisibiity(salaryText, "Field Required");
        } else if (!validateInput(sal)) {
            setVisibiity(salaryText, "Invalid input");
        } else {
            salaryText.setVisible(false);
        }
        if (type == null) {
            setVisibiity(radioButtonText, "Please select one");
        } else {
            radioButtonText.setVisible(false);
        }
        if (pass1.equals("") || pass2.equals("")) {
            setVisibiity(passwordText, "Required field");
        } else if (!Payroll.getNewPassword(pass1, pass2)) {
            setVisibiity(passwordText, "Passwords do not match");
            pass1 = "";
            pass2 = "";
        } else {
            passwordText.setVisible(false);
        }
        if (user.equals("")) {
            setVisibiity(usernameText, "Required Field");
        } else if (payroll.checkuserNameUnique(user)) {
            setVisibiity(usernameText, "UserName already Exists");
        } else {
            usernameText.setVisible(false);
        }
        int hours = 0;
        if (hourscheckBox.isSelected()) {
            if (hoursTextField.getText().equals("")) {
                setVisibiity(errorPromptCheckbox, "Value required");
                return;
            } else {
                errorPromptCheckbox.setVisible(false);
            }
            try {
                hours = Integer.parseInt(hoursTextField.getText());
            } catch (NumberFormatException e) {
                errorPromptCheckbox.setVisible(true);
                setVisibiity(errorPromptCheckbox, "Invalid input");
                return;
            }
        }
        int mainHours=0;
        if (radiobutton2.isSelected()){
            if (hTextField.getText().equals("")) {
                setVisibiity(radioButtonText, "Value required");
                return;
            } else {
                radioButtonText.setVisible(false);
            }
            try {
                mainHours = Integer.parseInt(hoursTextField.getText());
            } catch (NumberFormatException e) {
                radioButtonText.setVisible(true);
                setVisibiity(radioButtonText, "Invalid input");
                return;
            }
        }
        if (!(fullname.equals("") || sal.equals("") || user.equals("") || type == null || pass2.equals("") || pass1.equals("") || payroll.checkuserNameUnique(user))) {
            Employee emp = null;
            payroll.createNewEmployee(user, Double.parseDouble(sal), fullname, type, pass1, hours,mainHours);
            window.setStage("../UI/menuPage.fxml", "employee Menu");
        }
    }

    private void setVisibiity(Text fullnameText, String s) {
        fullnameText.setVisible(true);
        fullnameText.setText(s);
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
    void checkUnique(MouseEvent event) {
        System.out.println();
    }


    @FXML
    void clicked(MouseEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioGroup = new ToggleGroup();
        radiobutton1.setToggleGroup(radioGroup);
        radiobutton2.setToggleGroup(radioGroup);
        radiobutton1.selectedProperty().addListener((observableValue, aBoolean, selected) -> {
            if (selected) {
                type = EmployeeType.SALARIED;
                System.out.println(type);
            }
        });
        radiobutton2.selectedProperty().addListener((observableValue, aBoolean, selected) -> {
            if (selected) {
                type = EmployeeType.HOURLY;
            }
        });

    }
}
