package org.example.plant.protocol;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface DbCall {
    void systemDB(boolean usControl);

    void connectSDB(String rootname, String rootpass);

    void disconnectSDB() throws SQLException;

    void connectUDB();

    void disconnectUDB() throws SQLException;

    void createNewUser(String newname, String newpass, String email, String epass);

    ObservableList<Model> fetchTasksFromDB();

    void setUsnameG(String usnameg);

    void setUspassG(String uspassg);

    int getNextUserId(Connection connection, String nameTable, String idName);

    void insertTask(String nameTask, String textTask, Timestamp deadlineTask,
                    Timestamp createdTask, String statusTask, boolean execTask,
                    Timestamp lastCorrectTask, int assignedTask, String dependenciesTask);

    int getUserIdByName(String name);

    String getUserNameById(int id);

    void deleteTaskForUser(int userId);

    void deleteTaskForId(int idTask);

    void updateTaskStatus(int assignedTaskId, int curentTask, String newStatus, boolean fStatus) throws SQLException;

    void updateTaskDeathline(int assignedTaskId, int curentTask, String newDeadlineString);

    String getPasswById(int idUs);

    String getEmailById(int idUs);

    void generateSolvedTasksReport(Generator generator);

    void generateUnsolvedTasksReport(Generator generator);

    List<Forwarding> getTasks(int userId, Timestamp startDate, Timestamp endDate);
}
