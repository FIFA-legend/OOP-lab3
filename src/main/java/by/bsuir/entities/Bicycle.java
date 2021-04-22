package by.bsuir.entities;

import java.io.Serializable;

public class Bicycle extends Vehicle implements Serializable {

    private Type type;

    public Bicycle() {
    }

    public Bicycle(String brand, String model, String color, Type type) {
        super(brand, model, color);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
