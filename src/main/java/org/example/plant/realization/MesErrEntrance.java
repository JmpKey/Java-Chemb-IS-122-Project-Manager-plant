package org.example.plant.realization;

import org.example.plant.protocol.Message;

import javax.swing.*;

public class MesErrEntrance implements Message {
    private static Message instance;

    public static Message getInstance() {
        if (instance == null) {
            instance = new MesErrEntrance();
        }
        return instance;
    }

    @Override
    public void showMessage(String text) {
        JOptionPane.showMessageDialog(null, text,"Ошибка", JOptionPane.WARNING_MESSAGE);
    }
}
