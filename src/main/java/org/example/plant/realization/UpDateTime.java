package org.example.plant.realization;

import javafx.scene.control.TextField;
import org.example.plant.protocol.DateTame;
import org.example.plant.protocol.Metropolis;

import java.util.Objects;

public class UpDateTime implements DateTame {
    @Override
    public void updateNewDateTime(Metropolis capitalWinCont, TextField dat1_tf, TextField dat2_tf, TextField dat3_tf, TextField time1_tf, TextField time2_tf) {
        if (!Objects.equals(dat1_tf.getText(), "") & !Objects.equals(dat2_tf.getText(), "") & !Objects.equals(dat3_tf.getText(), "") & !Objects.equals(time1_tf.getText(), "") & !Objects.equals(time2_tf.getText(), "")) {
            capitalWinCont.getApplication().getDb().updateTaskDeathline(capitalWinCont.getApplication().getDb().getUserIdByName(capitalWinCont.getApplication().getUsnameG()), capitalWinCont.getSelectedIndex() + 1, dat1_tf.getText() + "-" + dat2_tf.getText() + "-" + dat3_tf.getText() + " " + time1_tf.getText() + ":" + time2_tf.getText() + ":" + "00");
        } //"yyyy-MM-dd HH:mm:ss"
    }
}
