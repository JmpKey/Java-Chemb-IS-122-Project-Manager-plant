package org.example.plant.realization;

import org.example.plant.protocol.DbCall;
import org.example.plant.protocol.Enigma;
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
    public void createNewUser(String userName, String pass, String eMail, String ePass) {
        DbCall db = DataBase.getInstance();
        db.systemDB(false);

        Enigma secure = PasswordManager.getInstance();

        try {
            db.createNewUser(userName, pass, eMail, secure.encryptPassword(ePass, userName, pass));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
