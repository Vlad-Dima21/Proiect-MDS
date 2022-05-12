package com.example.proiectmds.domain;

public class Food extends Product{
    private int amountInGrams;

    public Food(String name, double price, int amountInGrams) {
        super(name, price);
        this.amountInGrams = amountInGrams;
    }
}
