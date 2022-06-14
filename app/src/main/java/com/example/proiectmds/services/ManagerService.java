package com.example.proiectmds.services;

import com.example.proiectmds.domain.Client;
import com.example.proiectmds.domain.Manager;
import com.example.proiectmds.domain.Product;
import com.example.proiectmds.persistence.ManagerRepository;
import com.example.proiectmds.persistence.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManagerService {
    private ManagerRepository managerRepository = new ManagerRepository();

    public Manager loginManager(String email, String password) {
        /**
         * Search for the manager with given credentials.
         * If it doesn't exist, return null.
         */
        for (Manager manager : managerRepository.getAll()) {
            if (manager.getEmail().equals(email) && manager.getPassword().equals(password))
                return manager;
        }
        return null;
    }

    public List<Manager> getAllManagers() {
        return managerRepository.getAll();
    }

    public List<Product> getListOfProductsInStock(int managerId) {
        ProductRepository productRepository = new ProductRepository();
        List<Product> listOfProducs = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : managerRepository.get(managerId).getStocProduse().entrySet()) {
            if (entry.getValue() > 0) {
                listOfProducs.add(productRepository.get(entry.getKey()));
            }
        }

        return listOfProducs;
    }

    public boolean checkMail(String username) {
        for (Manager client : managerRepository.getAll())
        {
            if (client.getEmail().equals(username))
            {
                return true;
            }
        }
        return false;
    }
}
