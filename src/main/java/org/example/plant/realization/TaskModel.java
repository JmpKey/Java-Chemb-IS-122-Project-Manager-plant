package org.example.plant.realization;

import org.example.plant.protocol.Model;

import java.time.LocalDateTime;

public class TaskModel implements Model {
    private int idTask;
    private String nameTask;
    private String textTask;
    private LocalDateTime deadlineTask;
    private LocalDateTime createdTask;
    private int priorTask;
    private String statusTask;
    private LocalDateTime lastCorrectTask;
    private int assignedTask;
    private boolean execTask;
    private String dependenciesTask;

    @Override
    public int getIdTask() {
        return idTask;
    }

    @Override
    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    @Override
    public String getNameTask() {
        return nameTask;
    }

    @Override
    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    @Override
    public String getTextTask() {
        return textTask;
    }

    @Override
    public void setTextTask(String textTask) {
        this.textTask = textTask;
    }

    @Override
    public LocalDateTime getDeadlineTask() {
        return deadlineTask;
    }

    @Override
    public void setDeadlineTask(LocalDateTime deadlineTask) {
        this.deadlineTask = deadlineTask;
    }

    @Override
    public void setCreatedTask(LocalDateTime createdTask) {
        this.createdTask = createdTask;
    }

    @Override
    public LocalDateTime getCreatedTask() {
        return createdTask;
    }

    @Override
    public int getPriorTask() {
        return priorTask;
    }

    @Override
    public void setPriorTask(int priorTask) {
        this.priorTask = priorTask;
    }

    @Override
    public String getStatusTask() {
        return statusTask;
    }

    @Override
    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    @Override
    public Boolean getExecTask() { return execTask; }

    @Override
    public void setExecTask(Boolean execTask) { this.execTask = execTask; }

    @Override
    public String getDependenciesTask() { return dependenciesTask; }

    @Override
    public void setDependenciesTask(String dependenciesTask) { this.dependenciesTask = dependenciesTask; }

    @Override
    public LocalDateTime getLastCorrectTask() {
        return lastCorrectTask;
    }

    @Override
    public void setLastCorrectTask(LocalDateTime lastCorrectTask) {
        this.lastCorrectTask = lastCorrectTask;
    }

    @Override
    public int getAssignedTask() {
        return assignedTask;
    }

    @Override
    public void setAssignedTask(int assignedTask) {
        this.assignedTask = assignedTask;
    }
}
