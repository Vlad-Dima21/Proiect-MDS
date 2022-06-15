package com.example.proiectmds.persistence;

import com.example.proiectmds.domain.Manager;

import java.util.ArrayList;
import java.util.List;

public class ManagerRepository implements GenericRepository<Manager> {

    private static List<Manager> managerList = new ArrayList<>();

    static {
        managerList.add(new Manager("manager1@maga.ro", "abcdef", "Bd. Timișoara 17, București"));
        managerList.add(new Manager("manager2@maga.ro", "abcdef", "Șoseaua Cotroceni 15, București"));
        managerList.get(0).increaseNumberOfVisits();
        managerList.get(0).increaseNumberOfVisits();
    }

    @Override
    public void add(Manager entity) {
        managerList.add(entity);
    }

    @Override
    public Manager get(int id) {
        for (Manager manager : managerList) {
            if (manager.getId() == id) {
                return manager;
            }
        }
        return null;
    }
    @Override
    public void update(Manager entity) {
//      unused
    }

    @Override
    public void delete(Manager entity) {
//      unused
    }

    public List<Manager> getAll() {
        return new ArrayList<>(managerList);
    }

    @Override
    public int getSize() {
        return managerList.size();
    }
}
