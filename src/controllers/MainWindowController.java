package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class MainWindowController {
    public MainWindow window=new MainWindow();

    @FXML
    public void enterButton(ActionEvent event){
        System.out.println("Button clicked");
    }

}
