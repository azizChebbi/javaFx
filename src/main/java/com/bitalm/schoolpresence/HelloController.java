package com.bitalm.schoolpresence;

import Classes.Student;
import DAO.Authentication;
import com.bitalm.schoolpresence.Controllers.ItemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.event.ActionEvent;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController {
    @FXML
    public TextField usernamefield ;
    public TextField cinfield ;
    public Text errormessage ;

    // load new screen
    private void loadNewScreen(String file) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(file));
        Stage stage = new Stage() ;
        stage.setScene(new Scene(root));
        stage.show();
    }

    // ------------------ Sign In Screens -------------------
    private void getSignInScreen(ActionEvent event,String fxmlfile) throws IOException {
        loadNewScreen(fxmlfile);
    }
    public void getStudentSignInScreen(ActionEvent event) throws IOException {
        String fxmlfile = "SignIn/Student.fxml" ;
        getSignInScreen(event,fxmlfile);
    }
    public void getProfessorSignInScreen(ActionEvent event) throws IOException {
        String fxmlfile = "SignIn/Professor.fxml" ;
        getSignInScreen(event,fxmlfile);
    }
    // ------------------ End Sign In Screens -------------------


    // ------------------ Sign In Functions -------------------
    private void signIn(ActionEvent event,String file) throws IOException {
        loadNewScreen(file);
    }
    public void signInStudent(ActionEvent event) throws IOException {
        String name = usernamefield.getText() ;
        String cin = cinfield.getText() ;
        try {
            Authentication.signInStudent(name,cin);
            StudentChat.student = name ;
            signIn(event,"student_chat.fxml");
        } catch (Authentication.UserNotFoundException e) {
            errormessage.setText("Student not found, try again!");
        }

    }
    public void signInProfessor(ActionEvent event) throws IOException {
        String name = usernamefield.getText() ;
        String cin = cinfield.getText() ;

        try {
            Authentication.signInProfessor(name,cin);
            ClassroomController.professor = name ;
            signIn(event,"Classroom.fxml");
        } catch (Authentication.UserNotFoundException e) {
            errormessage.setText("Professor not found, try again!");
        }

    }
    // ------------------ End Sign In Functions -------------------

    // ------------------ Get All Students ------------------------


}