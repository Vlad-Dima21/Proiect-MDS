package com.example.proiectmds.services;

import com.example.proiectmds.domain.Beverage;
import com.example.proiectmds.domain.CleaningProduct;
import com.example.proiectmds.domain.Food;
import com.example.proiectmds.domain.PersonalCareProduct;
import com.example.proiectmds.domain.Product;
import com.example.proiectmds.helpers.ProductTuple;
import com.example.proiectmds.persistence.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProductService {
    private ProductRepository productRepository = new ProductRepository();
    private static List<HashMap<String, Integer>> productSalesForClients;
    private static List<HashMap<String, Integer>> productSalesForManagers;

    private static HashMap<Integer, Double> totalProfit = new HashMap<>();

    static {
        productSalesForClients = IntStream.range(0, 51).
                mapToObj(HashMap<String, Integer>::new).
                collect(Collectors.toList());

        productSalesForManagers = IntStream.range(0, 11).
                mapToObj(HashMap<String, Integer>::new).
                collect(Collectors.toList());
    }

    /** this method returns the required product while also
      updating the number of times it has been bought in total
     */
    public Product selectProductById(int clientId, int managerId, int productId) {
        Product product = productRepository.get(productId);
        HashMap<String, Integer> clientHash = productSalesForClients.get(clientId);
        clientHash.merge(product.getName(), 1, Integer::sum);

        HashMap<String, Integer> managerHash = productSalesForManagers.get(managerId);
        managerHash.merge(product.getName(), 1, Integer::sum);

//        totalProfit += product.getPrice();
        totalProfit.merge(managerId, product.getPrice(), Double::sum);
        return product;
    }

    /**
    returns the 3 best selling products with their corresponding percentages
     */
    public ProductTuple[] bestSellingProducts(int managerId) {
        ProductTuple[] bsp = new ProductTuple[3];

        Map.Entry<String, Integer> maxEntry1 = null;
        Map.Entry<String, Integer> maxEntry2 = null;
        Map.Entry<String, Integer> maxEntry3 = null;

        HashMap<String, Integer> managerHash = productSalesForManagers.get(managerId);

        double totalProfitForManager = totalProfit.get(managerId);

        for (Map.Entry<String, Integer> entry : managerHash.entrySet()) {
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
            Product product = productRepository.getByName(maxEntry1.getKey());
            bsp[0] = new ProductTuple(product,
                    maxEntry1.getValue() * product.getPrice() / totalProfitForManager * 100);
        } else {
            return bsp;
        }
        if (maxEntry2 != null) {
            Product product = productRepository.getByName(maxEntry2.getKey());
            bsp[1] = new ProductTuple(product,
                    maxEntry2.getValue() * product.getPrice() / totalProfitForManager * 100);
        } else {
            return bsp;
        }
        if (maxEntry3 != null) {
            Product product = productRepository.getByName(maxEntry3.getKey());
            bsp[2] = new ProductTuple(product,
                    maxEntry3.getValue() * product.getPrice() / totalProfitForManager * 100);
        } else {
            return bsp;
        }
        return bsp;
    }

    /**
     * Finds the product the client has bought the most
     * @param clientId
     * @return the product if it exists, otherwise null
     */
    public Product getFavouriteProduct(int clientId) {
        String nameOfBestSellingProduct = "";
        int salesNumberOfBestSellingProduct = 0;
        HashMap<String, Integer> clientHash = productSalesForClients.get(clientId);

        for (Map.Entry<String, Integer> entry : clientHash.entrySet()) {
            if (entry.getValue() > salesNumberOfBestSellingProduct) {
                nameOfBestSellingProduct = entry.getKey();
                salesNumberOfBestSellingProduct = entry.getValue();
            }
        }

        if (salesNumberOfBestSellingProduct > 0) {
            return productRepository.getByName(nameOfBestSellingProduct);
        }
        else {
            return null;
        }
    }

}