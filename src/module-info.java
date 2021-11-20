module EmployeeDatabase {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.base;
    requires javafx.base;
    opens com.employee.controllers;
    opens com.employee.domain;
    opens com.employee.UI;
}