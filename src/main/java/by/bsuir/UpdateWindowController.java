package by.bsuir;

import by.bsuir.entities.*;
import by.bsuir.service.Load;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class UpdateWindowController {

    @FXML
    private Button backButton;

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

    private final Vehicle updatableVehicle = TableWindowController.vehicle;

    @FXML
    void initialize() {
        typeInput.getItems().addAll(Type.SPORT, Type.MOUNTAIN, Type.CITY);
        brandInput.setText(TableWindowController.vehicle.getBrand());
        modelInput.setText(TableWindowController.vehicle.getModel());
        colorInput.setText(TableWindowController.vehicle.getColor());

        backButton.setOnAction(actionEvent -> {
            TableWindowController.vehicle = null;
            Load.getInstance().loadWindow("table_window.fxml");
        });

        if (updatableVehicle instanceof Bicycle) {
            typeInput.setVisible(true);
            typeInput.setValue(((Bicycle) updatableVehicle).getType());
        }

        if (updatableVehicle instanceof EnginedVehicle) {
            engineManufacturerInput.setVisible(true);
            engineVolumeInput.setVisible(true);
            engineVolumeInput.setText(Integer.toString(((EnginedVehicle) updatableVehicle).getEngine().getVolume()));
            engineManufacturerInput.setText(((EnginedVehicle) updatableVehicle).getEngine().getManufacturer());
        }

        if (updatableVehicle instanceof Motorcycle) {
            typeInput.setVisible(true);
            typeInput.setValue(((Motorcycle) updatableVehicle).getType());
        }

        if (updatableVehicle instanceof Car) {
            passengersInput.setVisible(true);
            passengersInput.setText(Integer.toString(((Car) updatableVehicle).getAmountOfPlaces()));
        }

        if (updatableVehicle instanceof Truck) {
            capacityInput.setVisible(true);
            capacityInput.setText(Integer.toString(((Truck) updatableVehicle).getCapacity()));
        }

        saveButton.setOnAction(actionEvent -> {
            updatableVehicle.setColor(colorInput.getText());
            if (updatableVehicle instanceof Bicycle) {
                ((Bicycle) updatableVehicle).setType(typeInput.getValue());
            }
            if (updatableVehicle instanceof EnginedVehicle) {
                Engine engine = new Engine(Integer.parseInt(engineVolumeInput.getText()), engineManufacturerInput.getText());
                ((EnginedVehicle) updatableVehicle).setEngine(engine);
            }
            if (updatableVehicle instanceof Motorcycle) {
                ((Motorcycle) updatableVehicle).setType(typeInput.getValue());
            }
            if (updatableVehicle instanceof Truck) {
                ((Truck) updatableVehicle).setCapacity(Integer.parseInt(capacityInput.getText()));
            }
            if (updatableVehicle instanceof Car) {
                ((Car) updatableVehicle).setAmountOfPlaces(Integer.parseInt(passengersInput.getText()));
            }
            Load.getInstance().loadWindow("table_window.fxml");
        });
    }
}
