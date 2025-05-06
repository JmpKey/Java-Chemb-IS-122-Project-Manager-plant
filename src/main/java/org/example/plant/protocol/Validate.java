package org.example.plant.protocol;

import java.sql.Timestamp;

public interface Validate {
    boolean validateInsertTask(String nameTask, String textTask, Timestamp deadlineTask,
                               Timestamp createdTask, String statusTask, boolean execTask,
                               Timestamp lastCorrectTask, int assignedTask, String dependenciesTask
    );

    boolean validateupdateNewDateTime(int assignedTaskId, int curentTask, String newDeadlineString);
}
