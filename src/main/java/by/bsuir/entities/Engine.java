package by.bsuir.entities;

import java.io.Serializable;

public class Engine implements Serializable {

    private int volume;

    private String manufacturer;

    public Engine() {
    }

    public Engine(int volume, String manufacturer) {
        this.volume = volume;
        this.manufacturer = manufacturer;
    }

    public int getVolume() {
        return volume;
    }

    public String getManufacturer() {
        return manufacturer;
    }
}
