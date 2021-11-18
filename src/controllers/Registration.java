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
    private final MainWindow window = new MainWindow();


    @FXML
    private Text fullnameText;


    private ToggleGroup radioGroup;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password1;

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
        if (!(fullname.equals("") || sal.equals("") || user.equals("") || type == null || pass2.equals("") || pass1.equals(""))) {
            domain.Employee emp = null;
//            submit.setDisable(false);
            payroll.createNewEmployee(user, Double.parseDouble(sal), fullname,type,pass1);
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
