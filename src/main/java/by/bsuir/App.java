package by.bsuir;

import by.bsuir.entities.Vehicle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class App extends Application {

    private static Stage stage;

    private static List<Vehicle> vehicles = new LinkedList<>();

    public static List<Vehicle> getVehicles() {
        return vehicles;
    }

    public static void setVehicles(List<Vehicle> vehicles) {
        App.vehicles = vehicles;
    }

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage st) throws IOException {
        stage = st;
        Scene scene = new Scene(loadFXML());
        stage.getIcons().add(new Image(App.class.getResourceAsStream("icon.png")));
        stage.setTitle("Cars");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main_window.fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}