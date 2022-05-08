package com.bitalm.schoolpresence.Controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class SenderController {
    @FXML
    public Text message ;

    public void setMessage(String msg){
        message.setText(msg);
    }
}
