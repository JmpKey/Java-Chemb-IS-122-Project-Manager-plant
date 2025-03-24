package org.example.plant;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.example.plant.protocol.AppCall;
import org.example.plant.protocol.Authorization;
import org.example.plant.protocol.Metropolis;
import org.example.plant.protocol.Model;
import org.example.plant.realization.BuildCapital;
import org.example.plant.realization.LoginUser;

public class CapitalWin {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane base_ap;

    @FXML
    private MenuItem create_menb;

    @FXML
    private Button dethline_bt;

    @FXML
    private MenuItem enter_menb;

    @FXML
    private Button exec_bt;

    @FXML
    private MenuItem exit_menb;

    @FXML
    private MenuBar header_menb;

    @FXML
    private Menu henter_menb;

    @FXML
    private Menu htask_menb;

    @FXML
    private Button prior_bt;

    @FXML
    private MenuItem registr_menb;

    @FXML
    private MenuItem plan_menb;

    @FXML
    public TableView<Model> table_tview;

    @FXML
    private TableColumn<Model, Integer> idColumn;

    @FXML
    private TableColumn<Model, String> nameColumn;

    @FXML
    private TableColumn<Model, String> textColumn;

    @FXML
    private TableColumn<Model, LocalDateTime> deadlineColumn;

    @FXML
    private TableColumn<Model, LocalDateTime> createdTask;

    @FXML
    private TableColumn<Model, String> statusTask;

    @FXML
    private TableColumn<Model, Boolean> execTask;

    @FXML
    private TableColumn<Model, LocalDateTime> lastCorrectTask;

    @FXML
    private TableColumn<Model, Integer> assignedTask;

    @FXML
    private TableColumn<Model, String> dependenciesTask;

    @FXML
    private Button update_bt;

    @FXML
    private Button del_bt;

    private AppCall application;

    Metropolis lain;

    public CapitalWin() {}

    @FXML
    void initialize() {
        lain = BuildCapital.getInstance();
        lain.setApplication(this.application);
        lain.setCapitalWin(this);
        lain.setTableView(table_tview);
        Authorization authorization = LoginUser.getInstance();
        authorization.setMetropolisController(lain);
        lain.fxmlInit(enter_menb, registr_menb, idColumn, nameColumn, textColumn, deadlineColumn, createdTask, statusTask, execTask, lastCorrectTask, assignedTask, create_menb, dependenciesTask, update_bt, exit_menb, exec_bt, del_bt, prior_bt, dethline_bt, plan_menb);
    }
}
