package org.example.plant.protocol;

import org.example.plant.CapitalWin;

public interface Authorization {
    void loginUser(String username, String userpass);

    void setMetropolisController(Metropolis controller);
}
