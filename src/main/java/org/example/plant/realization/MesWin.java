package org.example.plant.realization;

import org.example.plant.protocol.Message;

import javax.swing.*;

public class MesWin implements Message {
    @Override
    public void showMessage(String text) {
        JOptionPane.showMessageDialog(null, text);
    }
}
