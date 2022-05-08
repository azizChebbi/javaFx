package com.bitalm.schoolpresence.Controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ReceiverController {
    @FXML
    public Text message ;

    public void setMessage(String msg){
        message.setText(msg);
    }
}
