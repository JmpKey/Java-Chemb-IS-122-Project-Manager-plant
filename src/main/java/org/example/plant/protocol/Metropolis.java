package org.example.plant.protocol;

import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.plant.CapitalWin;
import org.example.plant.MainApplication;

import java.time.LocalDateTime;

public interface Metropolis {
    void setApplication(AppCall application);

    AppCall getApplication();

    void setCapitalWin(CapitalWin win);

    void fxmlInit(MenuItem enter_menb, MenuItem registr_menb, TableColumn<Model, Integer> idColumn, TableColumn<Model, String> nameColumn, TableColumn<Model, String> textColumn, TableColumn<Model, LocalDateTime> deadlineColumn, TableColumn<Model, LocalDateTime> createdTask, TableColumn<Model, String> statusTask, TableColumn<Model, Boolean> execTask, TableColumn<Model, LocalDateTime> lastCorrectTask, TableColumn<Model, Integer> assignedTask, MenuItem create_menb, TableColumn<Model, String> dependenciesTask, Button update_bt, MenuItem exit_menb, Button exec_bt, Button del_bt, Button prior_bt, Button dethline_bt, MenuItem plan_menb, MenuItem report_menb, MenuItem mess_menb);

    void initUserData(String user, String pass);

    void tableToModel();

    void refreshTable();

    void setTableView(TableView<Model> tableTview);

    CapitalWin getCapitalWin();

    int getSelectedIndex();

    String getFromEmail();

    String getEpass();

    String getToEmail();
}
