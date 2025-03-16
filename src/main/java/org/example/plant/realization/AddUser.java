package org.example.plant.realization;

import org.example.plant.protocol.DbCall;
import org.example.plant.protocol.Registration;

public class AddUser implements Registration {
    @Override
    public void createNewUser(String username, String pass, String email) {
        DbCall db = new DataBase();
        db.systemDB(false);
        db.createNewUser(username, pass, email);
    }
}
