package org.example.plant.realization;

import org.example.plant.ProvinceAddTask;
import org.example.plant.protocol.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewTask implements CreateTask {
    private Metropolis capitalWinCont;
    private ProvinceAddTask addTaskWin;

    private static CreateTask instance;

    public static CreateTask getInstance() {
        if (instance == null) {
            instance = new NewTask();
        }
        return instance;
    }

    @Override
    public void setCapitalWinCont(Metropolis capitalWinCont) { this.capitalWinCont = capitalWinCont; }

    @Override
    public void setAddTaskWin(ProvinceAddTask addTaskWin) { this.addTaskWin = addTaskWin; }

    @Override
    public void initTask() { runButton(); }

    @Override
    public void runButton() {
        DbCall insert = capitalWinCont.getApplication().getDb();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        insert.insertTask(addTaskWin.name_tf.getText(), addTaskWin.text_tf.getText(), toTimestamp(addTaskWin.date1_tf.getText(), addTaskWin.date2_tf.getText(), addTaskWin.date3_tf.getText(), addTaskWin.time1_tf.getText(), addTaskWin.time2_tf.getText()), currentTime, retStatus(), true, currentTime, addAfter(), addTaskWin.depen_tf.getText());
    }

    @Override
    public Timestamp toTimestamp(String dateString, String monthString, String yearString, String hourString, String minuteString) {
        String dateTimeString = String.format("%s.%s.%s %s:%s", dateString, monthString, yearString, hourString, minuteString);

        // We specify the date format with the full year
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Timestamp timestamp = null;

        try {
            // Parsing a string into a date
            Date parsedDate = format.parse(dateTimeString);

            // Converting the date to a Timestamp
            timestamp = new Timestamp(parsedDate.getTime());

            // Output the result
            System.out.println("Timestamp: " + timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timestamp;
    }

    public String retStatus() {
        if (addTaskWin.statussh_rb.isSelected()) { return "sh"; }
        else {
            if (addTaskWin.statush_rb.isSelected()) { return "h"; }
            else { if (addTaskWin.statusl_rb.isSelected()) { return "l"; } }
        }

        return null;
    }

    public int addAfter() {
        return capitalWinCont.getApplication().getDb().getUserIdByName(capitalWinCont.getApplication().getUsnameG());
    }
}
