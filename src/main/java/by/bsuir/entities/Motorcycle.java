package by.bsuir.entities;

import java.io.Serializable;

public class Motorcycle extends EnginedVehicle implements Serializable {

    private Type type;

    public Motorcycle() {
    }

    public Motorcycle(String brand, String model, String color, Engine engine, Type type) {
        super(brand, model, color, engine);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
