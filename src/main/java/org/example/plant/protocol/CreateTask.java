package org.example.plant.protocol;

import org.example.plant.ProvinceAddTask;

import java.sql.Timestamp;

public interface CreateTask {
    void runButton();

    Timestamp toTimestamp(String dateString, String monthString, String yearString, String hourString, String minuteString);

    String retStatus();

    int addAfter();

    void setCapitalWinCont(Metropolis capitalWinCont);

    void setAddTaskWin(ProvinceAddTask addTaskWin);

    void initTask();
}
