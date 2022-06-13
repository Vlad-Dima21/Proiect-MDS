package com.example.proiectmds.persistence;

import com.example.proiectmds.domain.Beverage;
import com.example.proiectmds.domain.Food;
import com.example.proiectmds.domain.PersonalCareProduct;
import com.example.proiectmds.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements GenericRepository<Product> {
    private static List<Product> products = new ArrayList<>();

//    those are placeholder products, later on these objects will be stored separately
    static {
        products.add(new Beverage("Coca-Cola", 5.99, 100, 500));
        products.add(new Beverage("Bere blonda Tuborg", 3.99, 100, 300));
        products.add(new Food("Chipsuri Lays cu sare", 6, 50, 140));
        products.add(new PersonalCareProduct("Gel de dus Palmolive", 22.9, 100));
        products.add(new PersonalCareProduct("Crema epilatoare", 22.9, 100));
        products.add(new Food("Soia boabe", 10.59, 90, 400));
        products.add(new Food("Burger de vita", 33.55, 50, 300));
        products.add(new Beverage("Apa minerala necarbogazoasa", 2.93, 200, 2000));
        products.add(new Food("Ciocolata cu alune Poiana", 3.12, 100, 90));
        products.add(new Food("Guma 5", 5.82, 100, 31));
    }

    @Override
    public void add(Product entity) {
        products.add(entity);
    }

    @Override
    public Product get(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public Product getByName(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public void update(Product entity) {
//      currently unused
    }

    @Override
    public void delete(Product entity) {
//      currently unused
    }

    @Override
    public int getSize() {
        return products.size();
    }
}
