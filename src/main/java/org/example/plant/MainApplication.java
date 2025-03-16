package org.example.plant;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.plant.protocol.AppCall;
import org.example.plant.realization.AppLIPC;

import java.io.IOException;
import java.sql.*;

public class MainApplication extends Application {
    AppCall app;

    @Override
    public void start(Stage stage) throws IOException {
        app = new AppLIPC();
        app.xtermGo(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}