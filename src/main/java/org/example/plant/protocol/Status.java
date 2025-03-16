package org.example.plant.protocol;

import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;

public interface Status {
    void updateStatus(Metropolis capitalWinCont, RadioButton statusl_rb, RadioButton statush_rb, RadioButton statussh_rb, CheckBox ftask_cb);
}
