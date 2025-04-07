package org.example.plant.realization;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.plant.*;
import org.example.plant.protocol.*;
import org.example.plant.realization.factory.MesFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BuildCapital implements Metropolis {
    private AppCall application;
    private CapitalWin capitalWin;
    private TableView<Model> tableView;
    private Message mesErr;
    private Message mes;
    private boolean loginFlag = false;
    private int selectedIndex;
    private ObservableList<Task> taskList;
    private String ePass;

    private static BuildCapital instance;

    public static Metropolis getInstance() {
        if (instance == null) {
            instance = new BuildCapital();
        }
        return instance;
    }

    @Override
    public void setApplication(AppCall application) {
        this.application = application;
    }

    @Override
    public AppCall getApplication() {
        return application;
    }

    @Override
    public void setCapitalWin(CapitalWin win) {
        this.capitalWin = win;
    }

    @Override
    public void initUserData(String user, String pass) {
        application = AppLIPC.getInstance();
        application.setUsnameG(user);
        application.setUspassG(pass);
        application.initUDB();
        Enigma dePass = PasswordManager.getInstance();
        try {
            ePass = dePass.decryptPassword(application.getDb().getPasswById(application.getDb().getUserIdByName(user)), user, pass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setTableView(TableView<Model> tableView) {
        this.tableView = tableView;
    }

    @Override
    public void tableToModel() {
        ObservableList<Model> tasks = application.getDb().fetchTasksFromDB();
        tableView.setItems(tasks);
        System.out.println("tableToModel()");
    }

    @Override
    public void refreshTable() {
        tableView.getItems().clear();

        // Загружаем новые данные из базы данных
        ObservableList<Model> tasks = application.getDb().fetchTasksFromDB();
        tableView.setItems(tasks);
    }

    @Override
    public CapitalWin getCapitalWin() {
        return capitalWin;
    }

    @Override
    public int getSelectedIndex() { return selectedIndex; }

    @Override
    public String getEpass() { return ePass; }

    @Override
    public void fxmlInit(MenuItem enter_menb, MenuItem registr_menb, TableColumn<Model, Integer> idColumn, TableColumn<Model, String> nameColumn, TableColumn<Model, String> textColumn, TableColumn<Model, LocalDateTime> deadlineColumn, TableColumn<Model, LocalDateTime> createdTask, TableColumn<Model, String> statusTask, TableColumn<Model, Boolean> execTask, TableColumn<Model, LocalDateTime> lastCorrectTask, TableColumn<Model, Integer> assignedTask, MenuItem create_menb, TableColumn<Model, String> dependenciesTask, Button update_bt, MenuItem exit_menb, Button exec_bt, Button del_bt, Button prior_bt, Button dethline_bt, MenuItem plan_menb) {
        // Устанавливаем фабрики для колонок
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idTask"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameTask"));
        textColumn.setCellValueFactory(new PropertyValueFactory<>("textTask"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadlineTask"));
        createdTask.setCellValueFactory(new PropertyValueFactory<>("createdTask"));
        statusTask.setCellValueFactory(new PropertyValueFactory<>("statusTask"));
        execTask.setCellValueFactory(new PropertyValueFactory<>("execTask"));
        lastCorrectTask.setCellValueFactory(new PropertyValueFactory<>("lastCorrectTask"));
        assignedTask.setCellValueFactory(new PropertyValueFactory<>("assignedTask"));
        dependenciesTask.setCellValueFactory(new PropertyValueFactory<>("dependenciesTask"));

        // Обработчик события для получения индекса выбранной строки
        tableView.setOnMouseClicked(event -> {
            selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                System.out.println("Выбранный индекс: " + selectedIndex);
            }
        });

        mesErr = MesFactory.createMessage(false);
        mes = MesFactory.createMessage(true);

        System.out.println("initialize()");

        registr_menb.setOnAction(actionEventR -> {
            // create dialog win
            FXMLLoader regLoaderR = new FXMLLoader();
            regLoaderR.setLocation(ProvinceReg.class.getResource("registr.fxml"));
            try {
                regLoaderR.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent rootR = regLoaderR.getRoot();
            Stage stageR = new Stage();
            stageR.setTitle("Регистрация");
            stageR.setScene(new Scene(rootR));
            stageR.showAndWait();
        });

        enter_menb.setOnAction(actionEventLog -> {
            if (!loginFlag) {
                FXMLLoader regLoaderLog = new FXMLLoader();
                regLoaderLog.setLocation(ProvinceLog.class.getResource("enter.fxml"));
                try {
                    Parent rootLog = regLoaderLog.load();
                    ProvinceLog logController = regLoaderLog.getController(); // Получаем контроллер
                    logController.capitalWinController = this; //  передаем ссылку на контроллер Metroplis
                    Stage stageLog = new Stage();
                    stageLog.setTitle("Вход");
                    stageLog.setScene(new Scene(rootLog));
                    stageLog.showAndWait();
                    loginFlag = true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else { mesErr.showMessage("Вы уже вошли!"); }
        });

        create_menb.setOnAction(actionEventAddTask -> {
            if (loginFlag) {
                FXMLLoader regLoaderAddTask = new FXMLLoader();
                regLoaderAddTask.setLocation(ProvinceAddTask.class.getResource("addtask.fxml"));
                try {
                    Parent rootAddTask = regLoaderAddTask.load();
                    ProvinceAddTask addTaskController = regLoaderAddTask.getController(); // Получаем контроллер
                    addTaskController.capitalWinCont = this; //  передаем ссылку на контроллер Metroplis
                    Stage stageAdd = new Stage();
                    stageAdd.setTitle("Добавить");
                    stageAdd.setScene(new Scene(rootAddTask));
                    stageAdd.showAndWait();
                    tableToModel();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else { mesErr.showMessage("Вы не вошли!"); }
        });

        update_bt.setOnAction(actionEventUpdateData -> {
            if (loginFlag) {
                tableToModel();
            } else { mesErr.showMessage("Вы не вошли!"); }
        });

        exit_menb.setOnAction(actionEventExitUs -> {
            if (loginFlag)
            {
                try {
                    ePass = null;
                    application.getDb().disconnectUDB();
                    tableView.getItems().clear();
                    loginFlag = false;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else { mesErr.showMessage("Вы не вошли!"); }
        });

        exec_bt.setOnAction(actionEventExec -> {
            if (loginFlag) {
                int indExec = tableView.getItems().get(selectedIndex).getAssignedTask();
                mes.showMessage("Создатель задачи: " + application.getDb().getUserNameById(indExec));
            }
            else { mesErr.showMessage("Вы не вошли!"); }
        });

        del_bt.setOnAction(actionEventTaskD -> {
            if (loginFlag) {
                int idUs = tableView.getItems().get(selectedIndex).getAssignedTask();
                if (Objects.equals(application.getUsnameG(), application.getDb().getUserNameById(idUs))) {
                    //application.getDb().deleteTaskForUser(idUs); // удаляет всё созданное пользователем
                    application.getDb().deleteTaskForId(tableView.getItems().get(selectedIndex).getIdTask());
                    tableToModel();
                }
                else { mesErr.showMessage("Вы не создатель задачи."); }
            }
            else { mesErr.showMessage("Вы не вошли!"); }
        });

        prior_bt.setOnAction(actionEventStatusU -> {
            if (loginFlag) {
                FXMLLoader stutusLoader = new FXMLLoader();
                stutusLoader.setLocation(ProvinceStatus.class.getResource("status.fxml"));
                try {
                    Parent rootStatus = stutusLoader.load();
                    ProvinceStatus statusController = stutusLoader.getController(); // Получаем контроллер
                    statusController.capitalWinCont = this; //  передаем ссылку на контроллер Metroplis
                    Stage stageStatus = new Stage();
                    stageStatus.setTitle("Статус");
                    stageStatus.setScene(new Scene(rootStatus));
                    stageStatus.showAndWait();
                    tableToModel();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else { mesErr.showMessage("Вы не вошли!"); }
        });

        dethline_bt.setOnAction(actionEventTimeU -> {
            if (loginFlag) {
                FXMLLoader timeLoader = new FXMLLoader();
                timeLoader.setLocation(ProvinceUpTime.class.getResource("uptime.fxml"));
                try {
                    Parent rootTime = timeLoader.load();
                    ProvinceUpTime timeController = timeLoader.getController(); // Получаем контроллер
                    timeController.capitalWinCont = this; //  передаем ссылку на контроллер Metroplis
                    Stage stageTime = new Stage();
                    stageTime.setTitle("Дата и время");
                    stageTime.setScene(new Scene(rootTime));
                    stageTime.showAndWait();
                    tableToModel();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else { mesErr.showMessage("Вы не вошли!"); }
        });

        plan_menb.setOnAction(actionEventPan -> {
            if (loginFlag) {
                TaskScheduler scheduler = ComparePlans.getInstance(); // Исправлено с ComparePlans на TaskScheduler
                scheduler.initTaskScheduler();
                Map<Integer, Task> taskMap = new HashMap<>();

                for (int i = 0; i < tableView.getItems().size(); i++) {
                    // Создание новой задачи на основе элементов из tableView
                    Task task = TaskWork.getInstance();
                    task.initTask(
                            tableView.getItems().get(i).getIdTask(),
                            tableView.getItems().get(i).getNameTask(),
                            Timestamp.valueOf(tableView.getItems().get(i).getDeadlineTask()),
                            tableView.getItems().get(i).getExecTask(),
                            tableView.getItems().get(i).getStatusTask()
                    );

                    taskMap.put(task.getId(), task);
                    // Добавляем зависимости для задач
                    task.addDependenciesFromString(tableView.getItems().get(i).getDependenciesTask(), taskMap);
                    scheduler.addTask(task);
                }

                try {
                    List<Task> plannedTasks = scheduler.scheduleTasks();

                    String stmes = "Список задач по порядку выполнения:\n";
                    for (Task t : plannedTasks) {
                        stmes += "Задача ID: " + t.getId() + ", имя: " + t.getNameTask() + ", статус: " + t.getPriorityValue() + "\n";
                    }
                    mes.showMessage(stmes);
                    EMailCall mail = new EmailSender();
                    mail.connectMail("FromEmail", application.getUsnameG(),"passwd", "toEmail", "theme", "text");
                } catch (Exception e) {
                    mesErr.showMessage(e.getMessage());
                }
            } else {
                mesErr.showMessage("Вы не вошли!");
            }
        });
    }
}
