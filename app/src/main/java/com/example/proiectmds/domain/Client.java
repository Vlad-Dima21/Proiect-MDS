package com.example.proiectmds.domain;

public class Client {
    private final int id;
    private static int idCounter;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String dateOfBirth;
    private Client linkedClient;

    static {
        idCounter = 0;
    }

    {
        id = ++idCounter;
    }

    public Client(String firstName, String lastName, String email, String phoneNumber, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
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
}
