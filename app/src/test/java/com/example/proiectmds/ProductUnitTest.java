package com.example.proiectmds;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.proiectmds.domain.Product;
import com.example.proiectmds.helpers.ProductTuple;
import com.example.proiectmds.persistence.ProductRepository;
import com.example.proiectmds.services.ProductService;

public class ProductUnitTest {
    @Test
    public void selectProductByIdIsCorrect() {
        int clientId = 1;
        int managerId = 1;
        int productId = 1;
        String nameOfProduct;

        nameOfProduct = new ProductRepository().get(productId).getName();
        ProductService test = new ProductService();
        Product product = test.selectProductById(clientId, managerId, productId);
        assertEquals(nameOfProduct, product.getName());
    }

    @Test
    public void bestSellingProductsIsCorrect() {
        int managerId = 1;
        int clinetId = 1;
        int bestProductId = 1;
        int leastProductId = 2;

        ProductService test = new ProductService();

//        select the best sellling product three times
        test.selectProductById(clinetId, managerId, bestProductId);
        test.selectProductById(clinetId, managerId, bestProductId);
        test.selectProductById(clinetId, managerId, bestProductId);

        test.selectProductById(clinetId, managerId, leastProductId);

        ProductTuple[] productTuples = test.bestSellingProducts(managerId);
        assertEquals(3 * 5.99 / (3 * 5.99 + 3.99) * 100, productTuples[0].percentage, 1e-15);
        assertEquals(3.99 / (3 * 5.99 + 3.99) * 100, productTuples[1].percentage, 1e-15);
        assertEquals(productTuples[2], null);
    }

    @Test
    public void getFavouriteProductIsCorrect() {
        int managerId = 1;
        int clinetId = 1;
        int productId1 = 1;
        int productId2 = 2;

        ProductService test = new ProductService();

//        select the best sellling product three times
        test.selectProductById(clinetId, managerId, productId1);
        test.selectProductById(clinetId, managerId, productId1);
        test.selectProductById(clinetId, managerId, productId1);

        test.selectProductById(clinetId, managerId, productId2);
        test.selectProductById(clinetId, managerId, productId2);
        test.selectProductById(clinetId, managerId, productId2);
        test.selectProductById(clinetId, managerId, productId2);

        assertEquals(productId2, test.getFavouriteProduct(clinetId).getId());
    }
}
