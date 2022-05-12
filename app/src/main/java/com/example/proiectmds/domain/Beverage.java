package com.example.proiectmds.domain;

public class Beverage extends Product{
    private int amountInMilliliters;

    public Beverage(String name, double price, int amountInMilliliters) {
        super(name, price);
        this.amountInMilliliters = amountInMilliliters;
    }
}
