package org.example.plant;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.plant.protocol.DbCall;
import org.example.plant.protocol.Registration;
import org.example.plant.realization.AddUser;

public class ProvinceReg {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anc_ap;

    @FXML
    private Button uscreate_bt;

    @FXML
    private TextField usmail_tf;

    @FXML
    private TextField usname_tf;

    @FXML
    private PasswordField uspass1_pf;

    @FXML
    private PasswordField uspass2_pf;

    @FXML
    private PasswordField uspasse_pf;

    @FXML
    void initialize() {
        uscreate_bt.setOnAction(event -> eRegActionButton());
    }

    private void eRegActionButton() {
        if(!Objects.equals(usname_tf.getText(), "") | !Objects.equals(uspass1_pf.getText(), "") | !Objects.equals(uspass2_pf.getText(), "") | !Objects.equals(usmail_tf.getText(), "") | !Objects.equals(uspasse_pf.getText(), "")) {
            if(Objects.equals(uspass1_pf.getText(), uspass2_pf.getText())) {
                Registration newUser = AddUser.getInstance();
                try {
                    newUser.createNewUser(usname_tf.getText(), uspass1_pf.getText(), usmail_tf.getText(), uspasse_pf.getText());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
