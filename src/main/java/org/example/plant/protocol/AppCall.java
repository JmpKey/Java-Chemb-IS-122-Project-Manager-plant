package org.example.plant.protocol;

import javafx.stage.Stage;

import java.io.IOException;

public interface AppCall {
    void xtermGo(Stage stage) throws IOException;

    void setUsnameG(String usnameG);

    String getUsnameG();

    void setUspassG(String uspassG);

    void initUDB();

    DbCall getDb();
}
