package by.bsuir.service;

import by.bsuir.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Load {

    private static Load INSTANCE;

    private Load() {}

    public static Load getInstance() {
        if (INSTANCE == null) {
            synchronized (Load.class) {
                if (INSTANCE == null) INSTANCE = new Load();
            }
        }
        return INSTANCE;
    }

    public void loadWindow(String str) {
        FXMLLoader loader = getLoader(str);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        App.getStage().setScene(new Scene(root));
        App.getStage().show();
    }

    private FXMLLoader getLoader(String fxml) {
        return new FXMLLoader(App.class.getResource(fxml));
    }
}
