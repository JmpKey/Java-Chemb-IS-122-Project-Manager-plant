package org.example.plant.realization;

import org.example.plant.protocol.Validate;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidateParameters implements Validate {
    private static Validate instance;

    public static Validate getInstance() {
        if (instance == null) {
            instance = new ValidateParameters();
        }
        return instance;
    }

    @Override
    public boolean validateInsertTask(String nameTask, String textTask, Timestamp deadlineTask,
                                      Timestamp createdTask, String statusTask, boolean execTask,
                                      Timestamp lastCorrectTask, int assignedTask, String dependenciesTask) {

        // Проверка строковых параметров на null и на пустоту
        if (nameTask == null || nameTask.isEmpty() ||
                textTask == null || textTask.isEmpty() ||
                statusTask == null || statusTask.isEmpty() ||
                dependenciesTask == null) {
            return false;
        }

        // Проверка временных меток на null
        if (deadlineTask == null || createdTask == null || lastCorrectTask == null) {
            return false;
        }

        // Проверка целочисленного параметра
        if (assignedTask < 0) { // Предположим, что assignedTask не может быть отрицательным
            return false;
        }

        if (!dependenciesTask.matches("(\\d+)(,(\\d+))*") && !dependenciesTask.equals("0")) {
            return false;
        }

        // Если все проверки пройдены, возвращаем true
        return true;
    }

    @Override
    public boolean validateupdateNewDateTime(int assignedTaskId, int curentTask, String newDeadlineString) {
        // Проверка на корректность идентификаторов задач
        if (assignedTaskId < 0 || curentTask < 0) {
            return false; // ID задач не могут быть отрицательными
        }

        // Проверка на корректность строки даты
        if (newDeadlineString == null || newDeadlineString.isEmpty()) {
            return false; // Строка не должна быть пустой или null
        }

        // Проверка формата даты
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false); // Устанавливаем строгий режим для проверки формата

        try {
            dateFormat.parse(newDeadlineString); // Если парсинг пройдет успешно, формат корректен
        } catch (ParseException e) {
            return false; // Если возникло исключение, формат некорректен
        }

        return true; // Все проверки пройдены успешно
    }
}
