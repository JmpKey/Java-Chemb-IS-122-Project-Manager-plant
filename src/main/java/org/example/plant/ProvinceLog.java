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
import org.example.plant.protocol.Metropolis;
import org.example.plant.realization.LoginUser;

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

    public Metropolis capitalWinController;

    @FXML
    void initialize() {
        entere_bt.setOnAction(event -> eLogActionButton());
    }

    private void eLogActionButton() {
        if(!Objects.equals(usename_tf.getText(), "") && !Objects.equals(usepass_pf.getText(), "")) {
            Authorization user = new LoginUser();
            user.setMetropolisController(this.capitalWinController);
            user.loginUser(usename_tf.getText(), usepass_pf.getText());
            capitalWinController.tableToModel();
            Stage stage = (Stage) entere_bt.getScene().getWindow();
            stage.close(); // Закрываем окно входа
        } else { System.out.println("Неверные данные."); }
    }
}
