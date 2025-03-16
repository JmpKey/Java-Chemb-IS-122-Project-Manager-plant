package org.example.plant.realization;

import org.example.plant.CapitalWin;
import org.example.plant.protocol.Authorization;
import org.example.plant.protocol.Metropolis;

public class LoginUser implements Authorization {
    private Metropolis capitalWinController;

    @Override
    public void loginUser(String name, String pass) {
        capitalWinController.initUserData(name, pass);
    }

    @Override
    public void setMetropolisController(Metropolis controller) {
        this.capitalWinController = controller;
    }
}
