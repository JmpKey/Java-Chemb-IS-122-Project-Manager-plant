package org.example.plant.realization;

import org.example.plant.protocol.FileNGen;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MergeGen implements FileNGen {
    private static FileNGen instance;
    public static FileNGen getInstance() {
        if (instance == null) {
            instance = new MergeGen();
        }
        return instance;
    }

    @Override
    public String mergeName(String mergeN) {
        String timestamp = new SimpleDateFormat("ydd_MM_yyyy").format(new Date());
        return mergeN + timestamp;
    }
}
