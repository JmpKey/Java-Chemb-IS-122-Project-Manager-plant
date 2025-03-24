package org.example.plant.realization;

import org.example.plant.protocol.DbCall;
import org.example.plant.protocol.Registration;

public class AddUser implements Registration {
    private static Registration instance;

    public static Registration getInstance() {
        if (instance == null) {
            instance = new AddUser();
        }
        return instance;
    }

    @Override
    public void createNewUser(String username, String pass, String email) {
        DbCall db = DataBase.getInstance();
        db.systemDB(false);
        db.createNewUser(username, pass, email);
    }
}
