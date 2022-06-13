package com.example.proiectmds.domain;

public class Manager {
    private final int id;
    private static int idCounter;

    private String email;
    private String password;
    private String location;

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
}
