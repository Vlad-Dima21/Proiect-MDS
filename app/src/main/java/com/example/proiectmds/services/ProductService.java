package com.example.proiectmds.services;

import com.example.proiectmds.domain.Beverage;
import com.example.proiectmds.domain.CleaningProduct;
import com.example.proiectmds.domain.Food;
import com.example.proiectmds.domain.PersonalCareProduct;
import com.example.proiectmds.domain.Product;
import com.example.proiectmds.helpers.ProductTuple;
import com.example.proiectmds.persistence.ProductRepository;

import java.util.HashMap;
import java.util.Map;

public class ProductService {
    private ProductRepository productRepository = new ProductRepository();
    static private HashMap<String, Integer> productSales = new HashMap<String, Integer>();
    private static int totalProfit = 0;

    public Product selectProductById(int id) {
    /* this method returns the required product while also
      updating the number of times it has been bought in total
     */
        Product product = productRepository.get(id);
        productSales.merge(product.getName(), 1, Integer::sum);
        totalProfit += product.getPrice();
        return product;
    }

    public ProductTuple[] bestSellingProducts() {
    /*
    returns the 3 best selling products in the store with their corresponding percentages
     */
        ProductTuple[] bsp = new ProductTuple[3];

        Map.Entry<String, Integer> maxEntry1 = null;
        Map.Entry<String, Integer> maxEntry2 = null;
        Map.Entry<String, Integer> maxEntry3 = null;

        for (Map.Entry<String, Integer> entry : productSales.entrySet()) {
            if (maxEntry1 == null || entry.getValue() > maxEntry1.getValue()) {
                maxEntry3 = maxEntry2;
                maxEntry2 = maxEntry1;
                maxEntry1 = entry;
            }
            else if (maxEntry2 == null || entry.getValue() > maxEntry2.getValue()) {
                maxEntry3 = maxEntry2;
                maxEntry2 = entry;
            }
            else if (maxEntry3 == null || entry.getValue() > maxEntry3.getValue()) {
                maxEntry3 = entry;
            }
        }

        if (maxEntry1 != null) {
            bsp[0] = new ProductTuple(productRepository.getByName(maxEntry1.getKey()),
                    maxEntry1.getValue());
        } else {
            return bsp;
        }
        if (maxEntry2 != null) {
            bsp[1] = new ProductTuple(productRepository.getByName(maxEntry2.getKey()),
                    maxEntry2.getValue());
        } else {
            return bsp;
        }
        if (maxEntry3 != null) {
            bsp[2] = new ProductTuple(productRepository.getByName(maxEntry3.getKey()),
                    maxEntry3.getValue());
        } else {
            return bsp;
        }
        return bsp;
    }

}