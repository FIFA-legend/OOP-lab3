package by.bsuir;

import by.bsuir.entities.*;
import by.bsuir.service.Load;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class RegistrationWindowController {

    @FXML
    private ToggleGroup group;

    @FXML
    private RadioButton bicycleRadio;

    @FXML
    private RadioButton carRadio;

    @FXML
    private RadioButton motorcycleRadio;

    @FXML
    private RadioButton truckRadio;

    @FXML
    private TextField brandInput;

    @FXML
    private TextField modelInput;

    @FXML
    private TextField colorInput;

    @FXML
    private TextField engineManufacturerInput;

    @FXML
    private TextField engineVolumeInput;

    @FXML
    private ChoiceBox<Type> typeInput;

    @FXML
    private TextField capacityInput;

    @FXML
    private TextField passengersInput;

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;

    @FXML
    void initialize() {
        typeInput.getItems().addAll(Type.SPORT, Type.CITY, Type.MOUNTAIN);

        backButton.setOnAction(actionEvent -> Load.getInstance().loadWindow("main_window.fxml"));

        bicycleRadio.setOnAction(actionEvent -> {
            engineManufacturerInput.setVisible(false);
            engineVolumeInput.setVisible(false);
            passengersInput.setVisible(false);
            capacityInput.setVisible(false);
            typeInput.setVisible(true);
        });

        motorcycleRadio.setOnAction(actionEvent -> {
            engineManufacturerInput.setVisible(true);
            engineVolumeInput.setVisible(true);
            passengersInput.setVisible(false);
            capacityInput.setVisible(false);
            typeInput.setVisible(true);
        });

        carRadio.setOnAction(actionEvent -> {
            engineManufacturerInput.setVisible(true);
            engineVolumeInput.setVisible(true);
            passengersInput.setVisible(true);
            capacityInput.setVisible(false);
            typeInput.setVisible(false);
        });

        truckRadio.setOnAction(actionEvent -> {
            engineManufacturerInput.setVisible(true);
            engineVolumeInput.setVisible(true);
            passengersInput.setVisible(false);
            capacityInput.setVisible(true);
            typeInput.setVisible(false);
        });

        saveButton.setOnAction(actionEvent -> {
            List<Vehicle> vehicles = App.getVehicles();
            vehicles.add(createVehicle());
            brandInput.clear();
            modelInput.clear();
            colorInput.clear();
            engineManufacturerInput.clear();
            engineVolumeInput.clear();
            passengersInput.clear();
            capacityInput.clear();
        });
    }

    private Vehicle createVehicle() {
        if (bicycleRadio.isSelected()) {
            return createBicycle();
        } else if (motorcycleRadio.isSelected()) {
            return createMotorcycle();
        } else if (carRadio.isSelected()) {
            return createCar();
        } else {
            return createTruck();
        }
    }

    private Bicycle createBicycle() {
        String brand = brandInput.getText();
        String model = modelInput.getText();
        String color = colorInput.getText();
        Type type = typeInput.getValue();
        return new Bicycle(brand, model, color, type);
    }

    private Motorcycle createMotorcycle() {
        String brand = brandInput.getText();
        String model = modelInput.getText();
        String color = colorInput.getText();
        int engineVolume = Integer.parseInt(engineVolumeInput.getText());
        String engineManufacturer = engineManufacturerInput.getText();
        Type type = typeInput.getValue();
        return new Motorcycle(brand, model, color, new Engine(engineVolume, engineManufacturer), type);
    }

    private Car createCar() {
        String brand = brandInput.getText();
        String model = modelInput.getText();
        String color = colorInput.getText();
        int engineVolume = Integer.parseInt(engineVolumeInput.getText());
        String engineManufacturer = engineManufacturerInput.getText();
        int passengers = Integer.parseInt(passengersInput.getText());
        return new Car(brand, model, color, new Engine(engineVolume, engineManufacturer), passengers);
    }

    private Truck createTruck() {
        String brand = brandInput.getText();
        String model = modelInput.getText();
        String color = colorInput.getText();
        int engineVolume = Integer.parseInt(engineVolumeInput.getText());
        String engineManufacturer = engineManufacturerInput.getText();
        int capacity = Integer.parseInt(capacityInput.getText());
        return new Truck(brand, model, color, new Engine(engineVolume, engineManufacturer), capacity);
    }

}
