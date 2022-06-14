package com.example.proiectmds.persistence;

import com.example.proiectmds.domain.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements GenericRepository<Client> {
    private static List<Client> clientList = new ArrayList<>();

    static {
        clientList.add(new Client("email1@cont.ro", "abcdef"));
        clientList.add(new Client("email2.@cont.ro", "abcdef"));
        clientList.add(new Client("email3.@cont.ro", "abcdef"));
    }

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

    public List<Client> getAll() {
        return new ArrayList<>(clientList);
    }

    @Override
    public int getSize() {
        return clientList.size();
    }
}
