package com.example.proiectmds.domain;

public class Client {
    private final int id;
    private static int idCounter;

    private String email;
    private String password;
    private Client linkedClient;

    static {
        idCounter = 0;
    }

    {
        id = ++idCounter;
    }

    public Client(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setLinkedClient(Client linkedClient) {
        this.linkedClient = linkedClient;
    }

    public Client getLinkedClient() {
        return linkedClient;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
