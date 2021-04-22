package by.bsuir.entities;

import java.io.Serializable;

public class Truck extends EnginedVehicle implements Serializable {

    private int capacity;

    public Truck() {
    }

    public Truck(String brand, String model, String color, Engine engine, int capacity) {
        super(brand, model, color, engine);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}
