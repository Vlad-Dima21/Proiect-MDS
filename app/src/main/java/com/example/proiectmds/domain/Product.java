package com.example.proiectmds.domain;

public abstract class Product {
    private final int id;
    private static int idCounter;

    protected String name;
    protected double price;
    protected int monthlyStock;

    static {
        idCounter = 0;
    }

    {
        id = ++idCounter;
    }

    public Product(String name, double price, int monthlyStock) {
        this.name = name;
        this.price = price;
        this.monthlyStock = monthlyStock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }
}
