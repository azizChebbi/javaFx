package com.bitalm.schoolpresence.Controllers;

import com.bitalm.schoolpresence.ClassroomController;
import com.bitalm.schoolpresence.MyListener;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;


public class ItemController {
    @FXML
    public ImageView image;

    @FXML
    public HBox item;

    @FXML
    public AnchorPane itemcontainer;

    @FXML
    public Text name;

    @FXML
    public void click(MouseEvent mouseEvent) throws IOException {
        myListener.onClickListener(name.getText());
    }

    public MyListener myListener ;

    public void setData(String nm,MyListener myListener){
        this.myListener = myListener ;
        name.setText(nm);
    }

    public void setStudent(MouseEvent mouseEvent){
        ClassroomController.student = name.getText() ;
        //ClassroomController.setSelectedStudent();
        System.out.println("clicked") ;
    }
}
