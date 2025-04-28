package org.example.plant;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.plant.protocol.Metropolis;
import org.example.plant.protocol.Search;
import org.example.plant.realization.SearchTask;

public class ProvinceSearch {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane divorce_ap;

    @FXML
    private Button kick_search_bt;

    @FXML
    private TextField search_tag_tf;

    public Metropolis capitalWinCont;

    @FXML
    void initialize() {
        kick_search_bt.setOnAction(event -> searchActionButton());
    }

    void searchActionButton() {
        if(!Objects.equals(search_tag_tf.getText(), "")) {
            Search searchTask = SearchTask.getInstance();
            searchTask.goSearch(capitalWinCont, search_tag_tf.getText());
        }
    }
}
