package org.example.plant;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.plant.protocol.DateTame;
import org.example.plant.protocol.Metropolis;
import org.example.plant.realization.BuildCapital;
import org.example.plant.realization.UpDateTime;

public class ProvinceUpTime {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField dat1_tf;

    @FXML
    private TextField dat2_tf;

    @FXML
    private TextField dat3_tf;

    @FXML
    private Button ok_but;

    @FXML
    private Label t1_lab;

    @FXML
    private Label t2_lab;

    @FXML
    private Label t3_lab;

    @FXML
    private Label t4_lab;

    @FXML
    private TextField time1_tf;

    @FXML
    private TextField time2_tf;

    @FXML
    void initialize() { ok_but.setOnAction(eventTime -> eTimeActionButton()); }

    void eTimeActionButton() {
        DateTame newDT = UpDateTime.getInstance();
        Metropolis capitalWinCont = BuildCapital.getInstance();
        newDT.updateNewDateTime(capitalWinCont, dat1_tf, dat2_tf, dat3_tf, time1_tf, time2_tf);
    }
}
