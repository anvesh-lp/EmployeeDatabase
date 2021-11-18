package controllers;

import domain.Payroll;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

public class EmployeeDetails {

    @FXML
    private Button detailsButton;

    @FXML
    private TextArea employeeDetailsText;

    @FXML
    private Label label;

    @FXML
    private Button logoutButton;

    @FXML
    private Button quitButton;

    private Payroll payroll=Payroll.getPayroll();

    private MainWindow window=MainWindow.getWindow();


    @FXML
    void OnDetailsHandler(ActionEvent event) {
        employeeDetailsText.setText(payroll.getCurrentUser().toString());
    }

    @FXML
    void onLogoutHandler(ActionEvent event) throws IOException {
        window.setStage("../UI/Login.fxml","Login");
    }

    @FXML
    void onQuitHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Do you wanna really wanna quit the company",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES){
            // ... user chose YES
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("You have been successfully terminated");
            window.setStage("../Login.fxml","Login");
        } else {
            alert.close();
        }
    }
}
