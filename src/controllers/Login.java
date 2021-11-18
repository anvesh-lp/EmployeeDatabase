package controllers;

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
    private MainWindow window=new MainWindow();

    @FXML
    void onSubmitHandler(ActionEvent event) throws NoSuchAlgorithmException, IOException {
            if (username.getText().equals("")){
                setVisibe(usernamePrompt,"User name required");
            }else {
                usernamePrompt.setVisible(false);
            }
            if (password.getText().equals("")){
                setVisibe(passwordprompt,"Field required");
            }else {
                passwordprompt.setVisible(false);
            }
            if (Payroll.getNewPassword(password.getText(),payroll.getCurrentUser().getPassword())){
                if (payroll.getCurrentId()==0) {
                    window.setStage("../UI/menuPage.fxml","Employee");
                }else {
                    window.setStage("../UI/employeeDetails.fxml","EmployeeDetails");
                }
            }else {
                setVisibe(passwordprompt,"Password Incorrect");
            }
        }




    public void setVisibe(Text text,String message){
        text.setVisible(true);
        text.setText(message);
    }

}
