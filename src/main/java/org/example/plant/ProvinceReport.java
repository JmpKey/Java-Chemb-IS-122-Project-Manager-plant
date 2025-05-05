package org.example.plant;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.plant.protocol.AppCall;
import org.example.plant.protocol.DbCall;
import org.example.plant.protocol.Generator;
import org.example.plant.protocol.Metropolis;
import org.example.plant.realization.AppLIPC;
import org.example.plant.realization.BuildCapital;
import org.example.plant.realization.DataBase;
import org.example.plant.realization.StatisticsGenerator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ProvinceReport {
    @FXML
    private TextField dat11_tf;

    @FXML
    private TextField dat1_tf;

    @FXML
    private TextField dat21_tf;

    @FXML
    private TextField dat2_tf;

    @FXML
    private TextField dat31_tf;

    @FXML
    private TextField dat3_tf;

    @FXML
    private Button ok_but;

    @FXML
    private Label t11_lab;

    @FXML
    private Label t1_lab;

    @FXML
    private Label t21_lab;

    @FXML
    private Label t2_lab;

    @FXML
    private Label t31_lab;

    @FXML
    private Label t3_lab;

    @FXML
    private Label t41_lab;

    @FXML
    private Label t4_lab;

    @FXML
    private Label text1_lab;

    @FXML
    private Label text2_lab;

    @FXML
    private TextField time11_tf;

    @FXML
    private TextField time1_tf;

    @FXML
    private TextField time21_tf;

    @FXML
    private TextField time2_tf;

    @FXML
    void initialize() {
        ok_but.setOnAction(event -> okActionButton());
    }

    void okActionButton() {
        if((!Objects.equals(dat1_tf.getText(), "")) | (!Objects.equals(dat11_tf.getText(), "")) | (!Objects.equals(dat2_tf.getText(), "")) | (!Objects.equals(dat21_tf.getText(), "")) | (!Objects.equals(dat3_tf.getText(), "")) | (!Objects.equals(dat31_tf.getText(), "")) | (!Objects.equals(time1_tf.getText(), "")) | (!Objects.equals(time11_tf.getText(), "")) | (!Objects.equals(time2_tf.getText(), "")) | (!Objects.equals(time21_tf.getText(), "")))
        {
            Generator generator = StatisticsGenerator.getInstance();
            Metropolis centrW = BuildCapital.getInstance();
            centrW.getApplication().getDb().generateSolvedTasksReport(generator);
            centrW.getApplication().getDb().generateUnsolvedTasksReport(generator);
            generator.generateTaskReport(centrW.getApplication().getDb(), centrW.getApplication().getUsnameG(), dat1_tf.getText() + "-" + dat2_tf.getText() + "-" + dat3_tf.getText() + " " + time1_tf.getText() + ":" + time2_tf.getText() + ":" + "00", dat11_tf.getText() + "-" + dat21_tf.getText() + "-" + dat31_tf.getText() + " " + time11_tf.getText() + ":" + time21_tf.getText() + ":" + "00");
        }
    }
}
