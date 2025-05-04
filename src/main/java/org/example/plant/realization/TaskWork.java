package org.example.plant.realization;

import org.example.plant.protocol.Task;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskWork implements Task {
    private int id;
    private String nameTask;
    private Timestamp deadline;
    private boolean execTask;
    private String priority;
    private List<Task> dependencies;

    @Override
    public void initTask(int id, String nameTask, Timestamp deadline, boolean execTask, String priority) {
        this.id = id;
        this.nameTask = nameTask;
        this.deadline = deadline;
        this.execTask = execTask;
        this.priority = priority;
        this.dependencies = new ArrayList<>();
    }

    @Override
    public void addDependency(Task task) {
        dependencies.add(task);
    }

    @Override
    public void addDependenciesFromString(String dependenciesString, Map<Integer, Task> taskMap) {
        if (dependenciesString == null || dependenciesString.isEmpty()) {
            return;
        }

        String[] ids = dependenciesString.split(",");
        for (String idStr : ids) {
            int id = Integer.parseInt(idStr.trim());
            Task dependentTask = taskMap.get(id);
            if (dependentTask != null) {
                addDependency(dependentTask);
            }
        }
    }

    @Override
    public Timestamp getDeadline() {
        return deadline;
    }

    @Override
    public int getPriorityValue() {
        return switch (priority) {
            case "sh" -> 3; // высокий
            case "h" -> 2; // средний
            case "l" -> 1; // низкий
            default -> 0; // неопределенный приоритет
        };
    }

    @Override
    public int getId() {
        return id; // возвращаем id
    }

    @Override
    public boolean isExecTask() {
        return execTask; // возвращаем execTask
    }

    @Override
    public List<Task> getDependencies() {
        return dependencies; // возвращаем зависимости
    }

    @Override
    public String getNameTask() {
        return nameTask;
    }
}
