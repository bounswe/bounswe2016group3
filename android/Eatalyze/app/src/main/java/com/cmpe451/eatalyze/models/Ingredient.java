package com.cmpe451.eatalyze.models;

/**
 * Created by ekrem on 27/10/2016.
 */

public class Ingredient {
    private String name;
    private double amount;

    public Ingredient(){}

    public Ingredient(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
