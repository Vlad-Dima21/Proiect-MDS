package com.example.proiectmds.services;

import com.example.proiectmds.domain.Client;
import com.example.proiectmds.domain.Product;
import com.example.proiectmds.persistence.ClientRepository;

public class ClientService {
    private ClientRepository clientRepository = new ClientRepository();

    public Client loginClient(String email, String password) {
        /**
         * Search for the client with given credentials.
         * If it doesn't exist, return null.
         */
        for (Client client : clientRepository.getAll()) {
            if (client.getEmail().equals(email) && client.getPassword().equals(password))
                return client;
        }
        return null;
    }

    public Client registerClient(String email, String password) {
        /**
         * Register client only if said client doesn't already exist
         */
        for (Client client : clientRepository.getAll()) {
            if (client.getEmail().equals(email) && client.getPassword().equals(password))
                return null;
        }
        Client client = new Client(email, password);
        clientRepository.add(client);
        return client;
    }
}
