package org.example.plant;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.plant.protocol.EMailCall;
import org.example.plant.protocol.Metropolis;
import org.example.plant.realization.EmailSender;

import javax.swing.*;

public class ProvinceMail {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button add_file_bt;

    @FXML
    private AnchorPane fon_ap;

    @FXML
    private Button go_mess_bt;

    @FXML
    private TextField mess_tf;

    @FXML
    private TextField head_tf;

    public Metropolis capitalWinCont;

    @FXML
    void initialize() {
        go_mess_bt.setOnAction(event -> goMessButton());
    }

    void goMessButton() {
        if(!Objects.equals(head_tf.getText(), "") && !Objects.equals(mess_tf.getText(), "")) {
            EMailCall mail = EmailSender.getInstance();
            mail.setMetropolisController(capitalWinCont);
            mail.mailStart(head_tf.getText(), mess_tf.getText());
        }
    }
}
