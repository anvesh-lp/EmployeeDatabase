package com.employee.controllers;

import com.employee.domain.Payroll;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainWindow extends Application {
    private static Stage stage;
    private static MainWindow window;

    public static void main(String[] args) {
        launch(args);
//        window=this;
    }

    public static MainWindow getWindow() {
        if (window == null) {
            window = new MainWindow();
            return window;
        }
        return window;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(new File("src/com/employee/UI/mainWindow.fxml").toURI().toURL());
        Parent root = loader.load();
//        Parent root = FXMLLoader.load(getClass().getResource("../UI/mainWindow.fxml"));
        stage = primaryStage;
        stage.setScene(new Scene(root));
        stage.setTitle("Main Window");
        stage.show();
//        This initializes the employee button reads the database file
        Payroll payroll = new Payroll();
    }


    public void setStage(String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(new File(fxmlFile).toURI().toURL());
        Parent root = loader.load();
//        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        if (stage.getScene() != null) {
            stage.setScene(new Scene(root));
            stage.setTitle(title);
        } else {
            stage.setScene(new Scene(root, 700, 600));
            stage.setTitle("Employee Database");
        }
        stage.show();
    }
}
