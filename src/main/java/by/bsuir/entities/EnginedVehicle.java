package by.bsuir.entities;

import java.io.Serializable;

public abstract class EnginedVehicle extends Vehicle implements Serializable {

    protected Engine engine;

    public EnginedVehicle() {
    }

    public EnginedVehicle(String brand, String model, String color, Engine engine) {
        super(brand, model, color);
        this.engine = engine;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
