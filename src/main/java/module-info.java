module com.bitalm.schoolpresence {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.bitalm.schoolpresence to javafx.fxml;
    exports com.bitalm.schoolpresence;
    exports com.bitalm.schoolpresence.Controllers;
    opens com.bitalm.schoolpresence.Controllers to javafx.fxml;
}