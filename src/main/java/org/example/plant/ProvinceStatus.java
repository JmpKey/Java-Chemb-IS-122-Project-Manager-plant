package org.example.plant;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import org.example.plant.protocol.Metropolis;
import org.example.plant.protocol.Status;
import org.example.plant.realization.UpStatus;

public class ProvinceStatus {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button setstatus_but;

    @FXML
    private RadioButton statush_rb;

    @FXML
    private RadioButton statusl_rb;

    @FXML
    private RadioButton statussh_rb;

    @FXML
    private CheckBox ftask_cb;

    public Metropolis capitalWinCont;

    @FXML
    void initialize() { setstatus_but.setOnAction(eventStatus -> eStatusActionButton()); }

    private void eStatusActionButton() {
        Status update = new UpStatus();
        update.updateStatus(capitalWinCont, statusl_rb, statush_rb, statussh_rb, ftask_cb);
    }
}
