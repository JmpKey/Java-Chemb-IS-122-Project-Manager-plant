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

        if (nameTask == null || nameTask.isEmpty() ||
                textTask == null || textTask.isEmpty() ||
                statusTask == null || statusTask.isEmpty() ||
                dependenciesTask == null) {
            return false;
        }

        if (deadlineTask == null || createdTask == null || lastCorrectTask == null) {
            return false;
        }

        if (assignedTask < 0) {
            return false;
        }

        if (!dependenciesTask.matches("(\\d+)(,(\\d+))*") && !dependenciesTask.equals("0")) {
            return false;
        }

        // If all checks are passed, we return true
        return true;
    }

    @Override
    public boolean validateupdateNewDateTime(int assignedTaskId, int curentTask, String newDeadlineString) {
        // Checking for the correctness of issue IDs
        if (assignedTaskId < 0 || curentTask < 0) {
            return false; // Task IDs cannot be negative
        }

        // Checking for the correctness of the date string
        if (newDeadlineString == null || newDeadlineString.isEmpty()) {
            return false; // The string must not be empty or null
        }

        // Checking the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false); // Setting a strict mode for format verification

        try {
            dateFormat.parse(newDeadlineString); // If the parsing is successful, the format is correct
        } catch (ParseException e) {
            return false; // If an exception occurs, the format is incorrect.
        }

        return true; // All checks passed successfully
    }
}
