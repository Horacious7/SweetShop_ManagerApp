package main.domain;

import java.io.Serializable;

public class BirthdayCake<ID> implements Identifiable<ID>, Serializable {
    private ID id;
    private String name;
    private String flavour;
    private double price;

    // Constructor without ID
    public BirthdayCake(String name, String flavour, double price) {
        this.name = name;
        this.flavour = flavour;
        this.price = price;
    }

    public BirthdayCake(ID id, String name, String flavour, double price) {
        this.id = id;
        this.name = name;
        this.flavour = flavour;
        this.price = price;
    }

    @Override
    public ID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFlavour() {
        return flavour;
    }

    public double getPrice() {
        return price;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + flavour + "," + price;
    }
}