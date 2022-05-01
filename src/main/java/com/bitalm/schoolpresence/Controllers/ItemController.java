package com.bitalm.schoolpresence.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ItemController {
    @FXML
    public ImageView image;

    @FXML
    public HBox item;

    @FXML
    public AnchorPane itemcontainer;

    @FXML
    public Text name;

    public void setData(String nm){
        name.setText(nm);
    }
}
