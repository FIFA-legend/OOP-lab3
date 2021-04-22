package by.bsuir;

import by.bsuir.service.Load;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainWindowController {

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button serializeButton;

    @FXML
    void initialize() {
        addButton.setOnAction(actionEvent -> Load.getInstance().loadWindow("registration_window.fxml"));

        removeButton.setOnAction(actionEvent -> Load.getInstance().loadWindow("table_window.fxml"));

        serializeButton.setOnAction(actionEvent -> Load.getInstance().loadWindow("serialization_window.fxml"));
    }
}
