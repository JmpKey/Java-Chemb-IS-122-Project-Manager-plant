package org.example.plant.realization;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.plant.CapitalWin;
import org.example.plant.MainApplication;
import org.example.plant.protocol.AppCall;
import org.example.plant.protocol.DbCall;

import java.io.IOException;

public class AppLIPC implements AppCall {
    private String usnameG;
    private String uspassG;
    private DbCall db;

    private static AppLIPC instance;

    public static AppCall getInstance() {
        if (instance == null) {
            instance = new AppLIPC();
        }
        return instance;
    }

    @Override
    public void xtermGo(Stage stage) throws IOException  {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("prev.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        stage.setTitle("PlanT");
        stage.setScene(scene);

        CapitalWin capitalWinController = fxmlLoader.getController();

        stage.show();
    }

    @Override
    public void setUsnameG(String usnameG) {
        this.usnameG = usnameG;
    }

    @Override
    public String getUsnameG() {
        return usnameG;
    }

    @Override
    public void setUspassG(String uspassG) {
        this.uspassG = uspassG;
    }

    @Override
    public void initUDB() {
        db = new DataBase();
        db.setUsnameG(usnameG);
        db.setUspassG(uspassG);
        db.systemDB(true);
    }

    @Override
    public DbCall getDb() { return db; }
}
