package org.example.plant.realization.factory;

import org.example.plant.protocol.Message;
import org.example.plant.realization.MesErrEntrance;
import org.example.plant.realization.MesWin;

public class MesFactory {
    private static MesFactory instance;

    public static Message createMessage(boolean type) {
        if (type) {
            return MesWin.getInstance();
        } else if (!type) {
            return MesErrEntrance.getInstance();
        }
        throw new IllegalArgumentException("Unknown type");
    }
}
