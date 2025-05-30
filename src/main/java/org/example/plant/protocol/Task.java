package org.example.plant.protocol;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface Task {
    void initTask(int id, String nameTask, Timestamp deadline, boolean execTask, String priority);

    void addDependency(Task task);

    void addDependenciesFromString(String dependenciesString, Map<Integer, Task> taskMap);

    Timestamp getDeadline();

    int getPriorityValue();

    int getId(); // Adding a getter for the id

    boolean isExecTask(); // Adding a getter for the execTask

    List<Task> getDependencies(); // Adding a getter for the dependencies

    String getNameTask();
}
