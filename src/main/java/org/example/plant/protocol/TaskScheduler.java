package org.example.plant.protocol;

import java.util.List;
import java.util.Set;

public interface TaskScheduler {
    void initTaskScheduler();

    void addTask(Task task);

    List<Task> scheduleTasks() throws Exception;

    void scheduleTask(Task task, List<Task> sortedTasks, Set<Task> visited, Set<Task> inStack) throws Exception;
}
