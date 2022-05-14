package com.example.proiectmds.persistence;

import com.example.proiectmds.domain.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements GenericRepository<Client> {
    private static List<Client> clientList = new ArrayList<>();

    @Override
    public void add(Client entity) {
        clientList.add(entity);
    }

    @Override
    public Client get(int id) {
        for (Client client : clientList) {
            if (client.getId() == id) {
                return client;
            }
        }
        return null;
    }

    @Override
    public void update(Client entity) {
//        currently unused
    }

    @Override
    public void delete(Client entity) {
//        currently unused
    }

    @Override
    public int getSize() {
        return clientList.size();
    }
}
