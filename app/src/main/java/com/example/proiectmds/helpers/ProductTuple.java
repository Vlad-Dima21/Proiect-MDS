package com.example.proiectmds.helpers;

import com.example.proiectmds.domain.Product;

public class ProductTuple {
    public Product product;
    public double percentage;

    public ProductTuple(Product product, double percentage) {
        this.product = product;
        this.percentage = percentage;
    }
}
