package org.example.plant.realization;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.plant.protocol.*;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;

public class DataBase implements DbCall {
    private String url = "";
    private Connection sdb = null;
    private Connection udb = null;
    private String usnameG;
    private String uspassG;
    private Message message;

    private static DbCall instance;

    public static DbCall getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public DataBase() { }

    @Override
    public void systemDB(boolean flagUs) {
        ConfigReader configReader = ConfigReader.getInstance();

        try {
            List<String> configValues = configReader.readConfigValuesDb();
            url = "jdbc:" + configValues.get(0) + "://" + configValues.get(1) + ":" + configValues.get(2) + "/" + configValues.get(5);
            if(flagUs == true) { connectUDB(); }
            else { connectSDB(configValues.get(3), configValues.get(4)); } // SU
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    @Override
    public void connectSDB(String su, String supass) {
        try {
            sdb = DriverManager.getConnection(url, su, supass);
            message = MesWin.getInstance();
            ConfigReader configReader = ConfigReader.getInstance();
            configReader.userRemoval(sdb);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void disconnectSDB() throws SQLException { sdb.close(); System.out.println("SDB end"); }

    @Override
    public void connectUDB() {
        try {
            if(sdb != null) { disconnectSDB(); }
            udb = DriverManager.getConnection(url, usnameG, uspassG);
            if(message == null) { message = MesErrEntrance.getInstance(); }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnectUDB() throws SQLException { udb.close(); System.out.println("UDB end"); }

    @Override
    public void createNewUser(String us, String uspass, String email, String epass) {
        try {
            Statement statement = sdb.createStatement();
            String createUserQ = String.format("CREATE USER %s PASSWORD '%s'", us, uspass);
            executeUpdate(statement, createUserQ, "Error creating user:");

            String grandSelTasksQ = String.format("GRANT SELECT ON TASKS TO %s", us);
            executeUpdate(statement, grandSelTasksQ, "Error grant sel tasks:");

            String grandSelUsersQ = String.format("GRANT SELECT ON USERS TO %s", us);
            executeUpdate(statement, grandSelUsersQ, "Error grant sel users:");

            String grandInsTasksQ = String.format("GRANT INSERT ON TASKS TO %s", us);
            executeUpdate(statement, grandInsTasksQ, "Error creating ins tasks:");

            String grandInsTasksGenQ = String.format("GRANT USAGE ON GENERATOR SEQ_TASKS_ID_TASK TO %s", us);
            executeUpdate(statement, grandInsTasksGenQ, "Error creating ins tasks gen:");

            String grandInsUsersQ = String.format("GRANT INSERT ON USERS TO %s", us);
            executeUpdate(statement, grandInsUsersQ, "Error creating ins users:");

            String grandInsUsersGenQ = String.format("GRANT USAGE ON GENERATOR SEQ_USERS_ID_US TO %s", us);
            executeUpdate(statement, grandInsUsersGenQ, "Error creating ins users gen:");

            String grandUpdTasks = String.format("GRANT UPDATE ON TASKS TO %s", us);
            executeUpdate(statement, grandUpdTasks, "Error creating upd tasks:");

            String grandDelTasks = String.format("GRANT DELETE ON TASKS TO %s", us);
            executeUpdate(statement, grandDelTasks, "Error creating del tasks:");

            message.showMessage("Регистрация успешна. Войдите под новым пользователем.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql1 = "SELECT COALESCE(MAX(ID_US), 0) + 1 AS Next_ID FROM USERS";
        int nextId = 0;

        try (PreparedStatement preparedStatement = sdb.prepareStatement(sql1);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                nextId = resultSet.getInt("Next_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql2 = "INSERT INTO USERS (ID_US, NAME_US, EMAIL_US, PASSW) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = sdb.prepareStatement(sql2)) {
            preparedStatement.setInt(1, nextId);
            preparedStatement.setString(2, us);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, epass);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            disconnectSDB();
        }
        catch (SQLException e) { e.printStackTrace(); }
    }

    void executeUpdate(Statement statement, String sql, String errorMessage) {
        try {
            if(statement.executeUpdate(sql) == 0) { System.out.println(errorMessage + "v"); }
        } catch (SQLException e) {

            message.showMessage(errorMessage + " " + e.getMessage());
        }
    }

    @Override
    public ObservableList<Model> fetchTasksFromDB() {
        ObservableList<Model> tasks = FXCollections.observableArrayList();

        if (udb == null) { systemDB(true); }
        if (sdb != null) {
            try {
                disconnectSDB();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(udb);

        try (Statement statement = udb.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM TASKS")) {

            while (resultSet.next()) {
                TaskModel task = new TaskModel();
                task.setIdTask(resultSet.getInt("ID_TASK"));
                task.setNameTask(resultSet.getString("NAME_TASK"));
                task.setTextTask(resultSet.getString("TEXT_TASK"));
                task.setDeadlineTask(resultSet.getTimestamp("DETHLINE_TASK").toLocalDateTime());
                task.setCreatedTask(resultSet.getTimestamp("CREATED_TASK").toLocalDateTime());
                task.setStatusTask(resultSet.getString("STATUS_TASK"));
                task.setExecTask(resultSet.getBoolean("EXEC_TASK"));
                task.setLastCorrectTask(resultSet.getTimestamp("LAST_CORRECT_TASK").toLocalDateTime());
                task.setAssignedTask(resultSet.getInt("ASSIGNED_TASK"));
                task.setDependenciesTask(resultSet.getString("DEPENDENCIES_TASK"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Количество задач, загруженных из базы данных: " + tasks.size());
        return tasks;
    }

    @Override
    public void setUsnameG(String usnameg) { this.usnameG = usnameg; }

    @Override
    public void setUspassG(String uspassg) { this.uspassG = uspassg; }

    @Override
    public int getNextUserId(Connection connection, String nameTable, String idName) {
        String sql = "SELECT COALESCE(MAX(" + idName + "), 0) + 1 AS Next_ID FROM " + nameTable;
        int nextId = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                nextId = resultSet.getInt("Next_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextId;
    }

    @Override
    public void insertTask(String nameTask, String textTask, Timestamp deadlineTask,
                           Timestamp createdTask, String statusTask, boolean execTask,
                           Timestamp lastCorrectTask, int assignedTask, String dependenciesTask) {

        int idTask = getNextUserId(udb, "TASKS", "ID_TASK");

        String insertSQL = "INSERT INTO TASKS (ID_TASK, NAME_TASK, TEXT_TASK, DETHLINE_TASK, CREATED_TASK, " +
                "STATUS_TASK, EXEC_TASK, LAST_CORRECT_TASK, ASSIGNED_TASK, DEPENDENCIES_TASK) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = udb.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, idTask);
            preparedStatement.setString(2, nameTask);
            preparedStatement.setString(3, textTask);
            preparedStatement.setTimestamp(4, deadlineTask);
            preparedStatement.setTimestamp(5, createdTask);
            preparedStatement.setString(6, statusTask);
            preparedStatement.setBoolean(7, execTask);
            preparedStatement.setTimestamp(8, lastCorrectTask);
            preparedStatement.setInt(9, assignedTask);
            preparedStatement.setString(10, dependenciesTask);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new task was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getUserIdByName(String name) {
        int userId = -1; // Значение по умолчанию, если пользователь не найден
        String query = "SELECT ID_US FROM USERS WHERE NAME_US = ?";

        try (PreparedStatement preparedStatement = udb.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt("ID_US");
                System.out.println(userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userId;
    }

    @Override
    public String getUserNameById(int id) {
        String name = null;

        String query = "SELECT NAME_US FROM USERS WHERE ID_US = ?";

        try (PreparedStatement preparedStatement = udb.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                name = resultSet.getString("NAME_US");
                System.out.println(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;
    }

    @Override
    public void deleteTaskForUser(int userId) {
        PreparedStatement pstmt = null;

        try {
            String sql = "DELETE FROM TASKS WHERE ASSIGNED_TASK = ?";
            pstmt = udb.prepareStatement(sql);

            pstmt.setInt(1, userId);

            int rowsAffected = pstmt.executeUpdate();

            System.out.println("Удалено задач: " + rowsAffected);
        } catch (SQLException se) {
            // Обработка ошибок JDBC
            System.err.println("SQLException: " + se.getMessage());
            System.err.println("SQLState: " + se.getSQLState());
            System.err.println("VendorError: " + se.getErrorCode());

            // Попытка отката транзакции (если она была начата)
            if (udb != null) {
                try {
                    udb.rollback();
                    System.err.println("Транзакция отменена");
                } catch (SQLException excep) {
                    System.err.println("Ошибка при откате: " + excep.getMessage());
                }
            }
        }
    }

    @Override
    public void deleteTaskForId(int idTask) {
        String deleteSQL = "DELETE FROM TASKS WHERE ID_TASK = ?";

        try (PreparedStatement preparedStatement = udb.prepareStatement(deleteSQL)) {

            preparedStatement.setInt(1, idTask); // Установка значения ID_TASK

            int rowsAffected = preparedStatement.executeUpdate(); // Выполнение запроса

            if (rowsAffected > 0) {
                System.out.println("Запись успешно удалена.");
            } else {
                System.out.println("Запись с указанным ID не найдена.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTaskStatus(int assignedTaskId, int curentTask, String newStatus, boolean fStatus) throws SQLException {
        String sql = "UPDATE TASKS SET STATUS_TASK = ?, LAST_CORRECT_TASK = ?, EXEC_TASK = ? WHERE ASSIGNED_TASK = ? AND ID_TASK = ?";

        try (PreparedStatement preparedStatement = udb.prepareStatement(sql)) {

            // Set the parameters for the prepared statement
            preparedStatement.setString(1, newStatus);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));  // Current timestamp
            preparedStatement.setBoolean(3, fStatus);
            preparedStatement.setInt(4, assignedTaskId);
            preparedStatement.setInt(5, curentTask);

            // Execute the update statement
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Task status updated successfully for ASSIGNED_TASK = " + assignedTaskId);
            } else {
                System.out.println("No tasks found with ASSIGNED_TASK = " + assignedTaskId);
            }

        } catch (SQLException e) {
            System.err.println("Error updating task status: " + e.getMessage());
            throw e; // Re-throw the exception to be handled by the calling code
        }
    }

    @Override
    public void updateTaskDeathline(int assignedTaskId, int curentTask, String newDeadlineString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); // dd-MM-yyyy  yyyy-MM-dd

        try {
            // 1.  Подготовка SQL запроса
            String sql = "UPDATE TASKS SET DETHLINE_TASK = ?, LAST_CORRECT_TASK = ? WHERE ID_TASK = ? AND ASSIGNED_TASK = ?";
            PreparedStatement preparedStatement = udb.prepareStatement(sql);

            // 2.  Преобразование даты из строки в java.sql.Timestamp
            Date parsedDate = dateFormat.parse(newDeadlineString); // Получаем java.util.Date
            Timestamp newDeadlineTimestamp = new Timestamp(parsedDate.getTime());  // Преобразуем в java.sql.Timestamp
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

            // 3. Установка параметров в SQL запрос
            preparedStatement.setTimestamp(1, newDeadlineTimestamp);
            preparedStatement.setTimestamp(2, currentTimestamp);
            preparedStatement.setInt(3, curentTask);
            preparedStatement.setInt(4, assignedTaskId);

            // 4. Выполнение запроса
            int rowsUpdated = preparedStatement.executeUpdate();

            // 5. Проверка результата
            if (rowsUpdated > 0) {
                System.out.println("Задача успешно обновлена.");
            } else {
                System.out.println("Задача с ID = " + curentTask + " и ASSIGNED_TASK = " + assignedTaskId + " не найдена.");
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при работе с базой данных: " + e.getMessage());
            e.printStackTrace(); // Важно выводить stack trace для отладки
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getUserIdByUserNameDel(Connection connection, String username) throws SQLException {
        String query = "SELECT ID_US FROM USERS WHERE NAME_US = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID_US");
            }
        }
        return null; // Пользователь не найден
    }

    public void deleteUser(Connection connection, Integer userId) throws SQLException {
        String deleteQuery = "DELETE FROM USERS WHERE ID_US = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        }
    }

    public void clearAssignedTasks(Connection connection, Integer userId) throws SQLException {
        String updateQuery = "UPDATE TASKS SET ASSIGNED_TASK = NULL WHERE ASSIGNED_TASK = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        }
    }

    public void deleteUserFromBd(Connection connection, String usNameDrop) {
        try {
            Statement statement = connection.createStatement();

            // SQL команда для удаления пользователя
            String sql = "DROP USER " + usNameDrop;

            // Выполнение SQL команды
            statement.executeUpdate(sql);
            System.out.println("Пользователь " + usNameDrop + " успешно удален.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPasswById(int idUs) {
        String pass = null;

        String query = "SELECT PASSW FROM USERS WHERE ID_US = ?";

        try (PreparedStatement preparedStatement = udb.prepareStatement(query)) {

            preparedStatement.setInt(1, idUs);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                pass = resultSet.getString("PASSW");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pass;
    }

    @Override
    public String getEmailById(int idUs) {
        String address = null;

        String query = "SELECT EMAIL_US FROM USERS WHERE ID_US = ?";

        try (PreparedStatement preparedStatement = udb.prepareStatement(query)) {

            preparedStatement.setInt(1, idUs);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                address = resultSet.getString("EMAIL_US");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return address;
    }

    @Override
    public void generateSolvedTasksReport(Generator generator) {
        String query = "SELECT U.NAME_US AS USER_NAME, COUNT(*) AS SOLVED_COUNT FROM TASKS T JOIN USERS U ON T.ASSIGNED_TASK = U.ID_US WHERE T.EXEC_TASK = 'true' GROUP BY U.NAME_US";
        Map<String, Integer> solvedTasks = new HashMap<>();

        try (Statement stmt = udb.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                solvedTasks.put(rs.getString("USER_NAME"), rs.getInt("SOLVED_COUNT"));
            }
        } catch (SQLException e) { e.printStackTrace(); }

        try {
            generator.createChartS(solvedTasks, "Solved Tasks", "solved_tasks_chart.png");
            generator.createHtmlReport("Solved Tasks", "solved_tasks_chart.png", "solved_tasks_report.html");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void generateUnsolvedTasksReport(Generator generator) {
        String query = "SELECT U.NAME_US AS USER_NAME, COUNT(*) AS UNSOLVED_COUNT FROM TASKS T JOIN USERS U ON T.ASSIGNED_TASK = U.ID_US WHERE T.EXEC_TASK = 'false' GROUP BY U.NAME_US";
        Map<String, Integer> unsolvedTasks = new HashMap<>();

        try (Statement stmt = udb.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                unsolvedTasks.put(rs.getString("USER_NAME"), rs.getInt("UNSOLVED_COUNT"));
            }
        } catch (SQLException e) { e.printStackTrace(); }

        try {
            generator.createChartS(unsolvedTasks, "Unsolved Tasks", "unsolved_tasks_chart.png");
            generator.createHtmlReport("Unsolved Tasks", "unsolved_tasks_chart.png", "unsolved_tasks_report.html");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Forwarding> getTasks(int userId, Timestamp startDate, Timestamp endDate) {
        String query = "SELECT NAME_TASK, STATUS_TASK, EXEC_TASK " +
                "FROM TASKS " +
                "WHERE ASSIGNED_TASK = ? AND CREATED_TASK BETWEEN ? AND ?";

        List<Forwarding> tasks = new ArrayList<>();

        try (PreparedStatement preparedStatement = udb.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setTimestamp(2, startDate);
            preparedStatement.setTimestamp(3, endDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String taskName = resultSet.getString("NAME_TASK");
                String statusTask = resultSet.getString("STATUS_TASK"); // Добавлено получение статуса задачи
                boolean isResolved = resultSet.getBoolean("EXEC_TASK");
                Forwarding task = new ForwardingTask();

                tasks.add(task.initForwardingTask(taskName, statusTask, isResolved)); // Используем конструктор
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return tasks;
    }
}
