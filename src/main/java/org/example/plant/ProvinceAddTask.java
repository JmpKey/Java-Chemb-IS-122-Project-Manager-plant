package org.example.plant;

import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.example.plant.protocol.CreateTask;
import org.example.plant.protocol.DbCall;
import org.example.plant.protocol.Metropolis;
import org.example.plant.realization.DataBase;
import org.example.plant.realization.NewTask;

import javax.swing.*;

public class ProvinceAddTask {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    Button addtask_but;

    @FXML
    private Label dat1_lab1;

    @FXML
    private Label dat2_lab;

    @FXML
    private Label dat3_lab;

    @FXML
    public TextField date1_tf;

    @FXML
    public TextField date2_tf;

    @FXML
    public TextField date3_tf;

    @FXML
    private Label depen_lab;

    @FXML
    public TextField depen_tf;

    @FXML
    private Label dethline_lab;

    @FXML
    private Label name_lab;

    @FXML
    public TextField name_tf;

    @FXML
    private Label status_lab;

    @FXML
    public RadioButton statush_rb;

    @FXML
    public RadioButton statusl_rb;

    @FXML
    public RadioButton statussh_rb;

    @FXML
    private Label text_lab;

    @FXML
    public TextField text_tf;

    @FXML
    public TextField time1_tf;

    @FXML
    public TextField time2_tf;

    @FXML
    private TextField after_tf;

    @FXML
    private Label time_lab;

    public Metropolis capitalWinCont;

    CreateTask newTask;

    @FXML
    void initialize() {
        addtask_but.setOnAction(event -> eLogActionAddButton());
    }

    private void eLogActionAddButton() {
        newTask = NewTask.getInstance();
        newTask.setCapitalWinCont(capitalWinCont);
        newTask.setAddTaskWin(this);
        newTask.initTask();
    }
}
