package com.example.proiectmds.domain;

import com.example.proiectmds.persistence.ProductRepository;

import java.util.HashMap;

public class Manager {
    private final int id;
    private static int idCounter;

    private String email;
    private String password;
    private String location;
    private HashMap<Integer, Integer> stocProduse;

    static {
        idCounter = 0;
    }

    {
        id = ++idCounter;
    }

    public Manager(String email, String password, String location) {
        this.email = email;
        this.password = password;
        this.location = location;
        this.stocProduse = new HashMap<>();
        for (Product product : new ProductRepository().getAll()) {
            stocProduse.put(product.getId(), product.monthlyStock);
        }
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<Integer, Integer> getStocProduse() {
        return stocProduse;
    }
}
