package com.bitalm.schoolpresence.Controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PresenceController {
    @FXML
    public Text name;

    public void setData(String nm){
        name.setText(nm);
    }
}
