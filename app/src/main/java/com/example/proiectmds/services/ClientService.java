package com.example.proiectmds.services;

import com.example.proiectmds.domain.Client;
import com.example.proiectmds.domain.Product;
import com.example.proiectmds.persistence.ClientRepository;

public class ClientService {
    private ClientRepository clientRepository = new ClientRepository();

    public boolean checkMail(String email)
    {
        for (Client client : clientRepository.getAll())
        {
            if (client.getEmail().equals(email))
            {
                return true;
            }
        }
        return false;
    }

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

    public Integer idPartnerOf(int clientId) {
        for (Client client : clientRepository.getAll()) {
            if (client.getId() == clientId && client.getLinkedClient() != null) {
                return client.getLinkedClient().getId();
            }
        }
        return null;
    }

    public void updateCredentials(String email, String password,int idClient)
    {
        Client client = clientRepository.get(idClient);
        if (client != null)
        {
            clientRepository.get(idClient).setEmail(email);
            clientRepository.get(idClient).setPassword(password);
        }
    }

    public int idByEmail(String email) {
        for (Client client : clientRepository.getAll())
        {
            if (client.getEmail().equals(email))
                return client.getId();
        }
        return -1;

    }

    public void addPartnerToAccount(int idClient, String emailOfPartner) {
            Client client = clientRepository.get(idClient);
            client.setLinkedClient(clientRepository.get(idByEmail(emailOfPartner)));
    }
}
