package controllers;

import domain.Payroll;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;


public class MainWindowController {
    public MainWindow window=new MainWindow();
    public Payroll payrol=Payroll.getPayroll();

//    @FXML
    public void enterButton(ActionEvent event) throws IOException {
        System.out.println("Button clicked");
        payrol.doMenu();
        if (payrol.getEmployees().size()==0){
//          Show Registration page
            window.setStage("../UI/Registration.fxml","");
        }else {
//          Show Login Page
            window.setStage("../UI/login.fxml","Login");
        }

    }

}
