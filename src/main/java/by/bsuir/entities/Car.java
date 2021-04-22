package by.bsuir.entities;

import java.io.Serializable;

public class Car extends EnginedVehicle implements Serializable {

    private int amountOfPlaces;
    public Car() {
    }

    public Car(String brand, String model, String color, Engine engine, int amountOfPlaces) {
        super(brand, model, color, engine);
        this.amountOfPlaces = amountOfPlaces;
    }

    public int getAmountOfPlaces() {
        return amountOfPlaces;
    }

    public void setAmountOfPlaces(int amountOfPlaces) {
        this.amountOfPlaces = amountOfPlaces;
    }
}
