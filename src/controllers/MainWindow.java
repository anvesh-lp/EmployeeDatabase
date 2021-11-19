package controllers;

import domain.Payroll;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {
    private static Stage stage;
    private static MainWindow window;

    public static void main(String[] args) {
        launch(args);
//        window=this;
    }

    public static MainWindow getWindow() {
        if (window==null){
            window=new MainWindow();
            return window;
        }
        return window;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("../UI/mainWindow.fxml"));
        stage=primaryStage;
        stage.setScene(new Scene(root));
        stage.setTitle("Main Window");
        stage.show();
        Payroll payroll=new Payroll();
//        MainWindowController controller=new MainWindowController();
//        controller.enterButton(new ActionEvent());
    }


    public void setStage(String fxmlFile,String title) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource(fxmlFile));
        if (stage.getScene()!=null){
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        }else {
            stage.setScene(new Scene(root, 700, 600));
            stage.setTitle("Employee Database");
            stage.show();
        }
    }
}
