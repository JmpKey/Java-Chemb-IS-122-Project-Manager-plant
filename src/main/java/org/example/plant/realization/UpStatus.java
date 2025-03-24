package org.example.plant.realization;

import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import org.example.plant.protocol.Metropolis;
import org.example.plant.protocol.Status;

import java.sql.SQLException;

public class UpStatus implements Status {
    private static Status instance;

    public static Status getInstance() {
        if (instance == null) {
            instance = new UpStatus();
        }
        return instance;
    }

    @Override
    public void updateStatus(Metropolis capitalWinCont, RadioButton statusl_rb, RadioButton statush_rb, RadioButton statussh_rb, CheckBox ftask_cb) {
        String status = null;
        boolean fsat = false;
        // далее выполнить обновление статуса и время последнего изменения, статус наверно должны менять все пользователи хз
        if (statusl_rb.isSelected()) { status = "l"; }
        if (statush_rb.isSelected()) { status = "h"; }
        if (statussh_rb.isSelected()) { status = "sh"; }

        if (ftask_cb.isSelected()) { fsat = true; }

        try {
            capitalWinCont.getApplication().getDb().updateTaskStatus(capitalWinCont.getApplication().getDb().getUserIdByName(capitalWinCont.getApplication().getUsnameG()), capitalWinCont.getSelectedIndex() + 1, status, fsat);
        }
        catch (SQLException e) { System.err.println("Error updating task status: " + e.getMessage()); }
    }
}
