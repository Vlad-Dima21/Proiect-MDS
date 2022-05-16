package com.example.proiectmds.domain;

public class Beverage extends Product{
    private int amountInMilliliters;

    public Beverage(String name, double price, int monthlyStock, int amountInMilliliters) {
        super(name, price, monthlyStock);
        this.amountInMilliliters = amountInMilliliters;
    }
}