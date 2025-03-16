package org.example.plant.protocol;

import java.time.LocalDateTime;

public interface Model {
    int getIdTask();

    void setIdTask(int idTask);

    void setNameTask(String nameTask);

    String getNameTask();

    String getTextTask();

    Boolean getExecTask();

    void setExecTask(Boolean execTask);

    String getDependenciesTask();

    void setDependenciesTask(String dependenciesTask);

    void setTextTask(String textTask);

    void setDeadlineTask(LocalDateTime deadlineTask);

    LocalDateTime getDeadlineTask();

    void setCreatedTask(LocalDateTime createdTask);

    LocalDateTime getCreatedTask();

    int getPriorTask();

    void setPriorTask(int priorTask);

    String getStatusTask();

    void setStatusTask(String statusTask);

    LocalDateTime getLastCorrectTask();

    void setLastCorrectTask(LocalDateTime lastCorrectTask);

    int getAssignedTask();

    void setAssignedTask(int assignedTask);
}
