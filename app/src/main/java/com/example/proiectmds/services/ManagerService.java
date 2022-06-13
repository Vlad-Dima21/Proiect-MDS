package com.example.proiectmds.services;

import com.example.proiectmds.domain.Client;
import com.example.proiectmds.domain.Manager;
import com.example.proiectmds.persistence.ManagerRepository;

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
}
