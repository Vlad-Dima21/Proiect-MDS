package com.example.proiectmds.services;

import com.example.proiectmds.domain.Client;
import com.example.proiectmds.domain.Manager;
import com.example.proiectmds.persistence.ManagerRepository;

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

    public String locationOfManager(int managerId) {
        return managerRepository.get(managerId).getLocation();
    }

    public List<Integer> getListOfProductsInStock(int managerId) {
        List<Integer> listOfProductIds = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : managerRepository.get(managerId).getStocProduse().entrySet()) {
            if (entry.getValue() > 0) {
                listOfProductIds.add(entry.getKey());
            }
        }

        return listOfProductIds;
    }
}
