package controllers;

import domain.Payroll;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PayrollRun implements Initializable {

    private Payroll payroll = Payroll.getPayroll();
    private MainWindow window = MainWindow.getWindow();

    @FXML
    private Button payrollOkButton;

    @FXML
    private TextArea payrollTextArea;

    @FXML
    void onOkClick(ActionEvent event) throws IOException {
        window.setStage("../UI/menuPage.fxml", "Boss Menu");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        payrollTextArea.setText(payroll.runPayroll());
    }
}
