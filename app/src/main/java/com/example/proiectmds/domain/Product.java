package com.example.proiectmds.domain;

import java.util.List;

public abstract class Product {
    private final int id;
    private static int idCounter;

    protected String name;
    protected double price;

    static {
        idCounter = 0;
    }

    {
        id = ++idCounter;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
