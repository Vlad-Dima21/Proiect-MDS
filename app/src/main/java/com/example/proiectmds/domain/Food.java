package com.example.proiectmds.domain;

public class Food extends Product{
    private int amountInGrams;

    public Food(String name, double price, int monthlyStock, int amountInGrams) {
        super(name, price, monthlyStock);
        this.amountInGrams = amountInGrams;
    }
}