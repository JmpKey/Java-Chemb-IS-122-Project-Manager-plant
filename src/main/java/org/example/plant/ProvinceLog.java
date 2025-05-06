package org.example.plant;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.plant.protocol.Authorization;
import org.example.plant.protocol.Message;
import org.example.plant.protocol.Metropolis;
import org.example.plant.realization.BuildCapital;
import org.example.plant.realization.LoginUser;
import org.example.plant.realization.MesErrEntrance;

public class ProvinceLog  {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button entere_bt;

    @FXML
    private AnchorPane panele_ap;

    @FXML
    private TextField usename_tf;

    @FXML
    private PasswordField usepass_pf;

    @FXML
    void initialize() {
        entere_bt.setOnAction(event -> eLogActionButton());
    }

    private void eLogActionButton() {
        if(!Objects.equals(usename_tf.getText(), "") && !Objects.equals(usepass_pf.getText(), "")) {
            Authorization user = LoginUser.getInstance();
            Metropolis capitalWinController = BuildCapital.getInstance();
            user.setMetropolisController(capitalWinController);
            user.loginUser(usename_tf.getText(), usepass_pf.getText());
            capitalWinController.tableToModel();
            Stage stage = (Stage) entere_bt.getScene().getWindow();
            stage.close(); // Закрываем окно входа
        } else {
            Message errWin = MesErrEntrance.getInstance();
            errWin.showMessage("Неверные данные");
        }
    }
}
