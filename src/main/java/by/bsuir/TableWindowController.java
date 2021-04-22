package by.bsuir;

import by.bsuir.entities.Vehicle;
import by.bsuir.service.Load;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.LinkedList;
import java.util.List;

public class TableWindowController {

    @FXML
    private Button backButton;

    @FXML
    private TableView<VehicleDTO> vehicleTable;

    @FXML
    private TableColumn<VehicleDTO, String> idColumn;

    @FXML
    private TableColumn<VehicleDTO, String> typeColumn;

    @FXML
    private TableColumn<VehicleDTO, String> brandColumn;

    @FXML
    private TableColumn<VehicleDTO, String> modelColumn;

    @FXML
    private TableColumn<VehicleDTO, String> colorColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private Button updateButton;

    static Vehicle vehicle;

    @FXML
    void initialize() {
        backButton.setOnAction(actionEvent -> Load.getInstance().loadWindow("main_window.fxml"));

        idColumn.setCellValueFactory(id -> id.getValue().id.asString());
        typeColumn.setCellValueFactory(type -> type.getValue().type);
        brandColumn.setCellValueFactory(brand -> brand.getValue().brand);
        modelColumn.setCellValueFactory(model -> model.getValue().model);
        colorColumn.setCellValueFactory(color -> color.getValue().color);

        fillTable();

        deleteButton.setOnAction(actionEvent -> {
            VehicleDTO vehicle = vehicleTable.getSelectionModel().getSelectedItem();
            if (vehicle != null) {
                int index = vehicle.id.getValue();
                App.getVehicles().remove(index - 1);
                fillTable();
            }
        });

        updateButton.setOnAction(actionEvent -> {
            VehicleDTO v = vehicleTable.getSelectionModel().getSelectedItem();
            if (v != null) {
                vehicle = App.getVehicles().get(v.id.getValue() - 1);
                Load.getInstance().loadWindow("update_window.fxml");
            }
        });
    }

    private void fillTable() {
        List<VehicleDTO> dto = new LinkedList<>();
        int i = 1;
        for (Vehicle vehicle : App.getVehicles()) {
            dto.add(new VehicleDTO(i++, vehicle.getClass().getSimpleName(), vehicle.getBrand(), vehicle.getModel(), vehicle.getColor()));
        }
        ObservableList<VehicleDTO> list = FXCollections.observableList(dto);
        vehicleTable.setItems(list);
    }

    private static class VehicleDTO {

        IntegerProperty id;

        StringProperty type;

        StringProperty brand;

        StringProperty model;

        StringProperty color;

        public VehicleDTO(int id, String type, String brand, String model, String color) {
            this.id = new SimpleIntegerProperty(id);
            this.type = new SimpleStringProperty(type);
            this.brand = new SimpleStringProperty(brand);
            this.model = new SimpleStringProperty(model);
            this.color = new SimpleStringProperty(color);
        }
    }
}
