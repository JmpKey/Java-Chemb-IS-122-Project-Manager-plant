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

    int getId(); // Добавляем геттер для id

    boolean isExecTask(); // Добавляем геттер для execTask

    List<Task> getDependencies(); // Добавляем геттер для dependencies

    String getNameTask();
}
