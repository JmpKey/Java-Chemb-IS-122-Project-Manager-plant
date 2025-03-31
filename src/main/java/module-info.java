module org.example.plant {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.graphics;
    requires java.desktop;
    requires java.mail;

    opens org.example.plant to javafx.fxml;
    exports org.example.plant;
    exports org.example.plant.protocol;
    opens org.example.plant.protocol to javafx.fxml;
    exports org.example.plant.realization;
    opens org.example.plant.realization to javafx.fxml;
}