package org.example.plant.realization;

import org.example.plant.protocol.Task;
import org.example.plant.protocol.TaskScheduler;

import java.util.*;

public class ComparePlans implements TaskScheduler {
    private List<Task> tasks;

    private static TaskScheduler instance;

    public static TaskScheduler getInstance() {
        if (instance == null) {
            instance = new ComparePlans();
        }
        return instance;
    }

    @Override
    public void initTaskScheduler() {
        tasks = new ArrayList<>();
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public List<Task> scheduleTasks() throws Exception {
        List<Task> sortedTasks = new ArrayList<>();
        Set<Task> visited = new HashSet<>();
        Set<Task> inStack = new HashSet<>();

        tasks.sort(Comparator.comparing(Task::getDeadline)
                .thenComparing(Comparator.comparingInt(Task::getPriorityValue).reversed()));

        for (Task task : tasks) {
            if (!task.isExecTask()) {
                scheduleTask(task, sortedTasks, visited, inStack);
            }
        }

        return sortedTasks;
    }

    @Override
    public void scheduleTask(Task task, List<Task> sortedTasks, Set<Task> visited, Set<Task> inStack) throws Exception {
        if (inStack.contains(task)) {
            throw new Exception("Ошибка: циклическая зависимость обнаружена для задачи ID: " + task.getId());
        }

        if (visited.contains(task)) {
            return; // Задача уже запланирована
        }

        visited.add(task);
        inStack.add(task); // Добавляем в стек текущую задачу

        for (Task dependency : task.getDependencies()) {
            if (!dependency.isExecTask()) {
                scheduleTask(dependency, sortedTasks, visited, inStack);
            }
        }

        inStack.remove(task); // Убираем задачу из стека
        sortedTasks.add(task);
    }
}
