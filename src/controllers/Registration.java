package controllers;

import domain.Payroll;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
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
    void onSubmit(ActionEvent event) throws IOException {

        System.out.println("clicked");
        String fullname = name.getText();

        String user = username.getText();
        String sal = salary.getText();
        System.out.println(user);
        System.out.println(sal);
        if (fullname.equals("")) {
            fullnameText.setVisible(true);
            fullnameText.setText("Field Required");
        } else {
            fullnameText.setVisible(false);
        }
        if (Objects.equals(user, "")) {
            usernameText.setVisible(true);
            usernameText.setText("Field Required");
        } else if (payroll.checkUnique(user)) {
            usernameText.setVisible(true);
            usernameText.setText("UserName Already Exists");
        } else {
            usernameText.setVisible(false);
        }
        if (sal.equals("")) {
            salaryText.setVisible(true);
            salaryText.setText("Field Required");
        } else if (!validateInput(sal)) {
            salaryText.setVisible(true);
            salaryText.setText("Invalid input");
        } else {
            salaryText.setVisible(false);
        }

        if (type == null) {
            radioButtonText.setText("Please select one");
            radioButtonText.setVisible(true);
        } else {
            radioButtonText.setVisible(false);
        }
        if (!(fullname.equals("") || sal.equals("") || user.equals("") || type == null)) {
//            submit.setDisable(false);
            window.setStage("../UI/menuPage", "employee Menu");
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
    void checkUnique(MouseEvent event) {
        System.out.println();
    }


    @FXML
    void clicked(MouseEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Object ref = new Object() {
            public EmployeeType payrollType = null;

            public EmployeeType getPayrollType() {
                return payrollType;
            }

            public void setPayrollType(EmployeeType payrollType) {
                this.payrollType = payrollType;
            }
        };
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
