package com.bitalm.schoolpresence;
import java.sql.*;

import DAO.Authentication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        Parent fxmll = FXMLLoader.load(getClass().getResource("Classroom.fxml")) ;
        Scene scene = new Scene(fxmll);
        stage.setTitle("Welcome to Classroom 51");
        stage.setScene(scene);
        stage.show();
    }





















    public static void main(String[] args) {
        launch();
    }
}