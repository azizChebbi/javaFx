package com.bitalm.schoolpresence;

import Classes.Student;
import DAO.Students;
import com.bitalm.schoolpresence.Controllers.ItemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClassroomController implements Initializable {
    @FXML
    public VBox students;

    public List<Student> studentslist = new ArrayList<>() ;

    private void getAllStudents(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
                List<String> studentslist = Students.getStudents() ;
                for (int i = 0; i < studentslist.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("item.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    ItemController itemController = fxmlLoader.getController();
                    itemController.setData(studentslist.get(i));
                    students.getChildren().add(anchorPane) ;
                    System.out.println(anchorPane.toString());
                    System.out.println("aziz");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }




    }
}
