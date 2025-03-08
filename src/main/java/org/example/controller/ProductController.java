package org.example.controller;

import org.example.modal.dao.ProductDaoImp;
import org.example.modal.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductController {

    private final ProductDaoImp productDao;
    private final List<Product> pendingProducts;
    private boolean saveRequested;

    public ProductController(ProductDaoImp productDao) {
        this.productDao = productDao;
        this.pendingProducts = new ArrayList<>();
        this.saveRequested = false;
    }

    // Add product to the pending list
    public void addProduct(Product product) {
        productDao.addProduct(product, pendingProducts);
        System.out.println("Product added to the pending list.");
    }

    // Save products to the database
    public void saveProducts() {
        saveRequested = true;
        if (saveRequested) {
            productDao.save(pendingProducts);
            System.out.println("Products saved to the database.");
        }
    }

    // Cancel pending products
    public void cancelPending() {
        pendingProducts.clear();
        saveRequested = false;
        System.out.println("Cancelled pending products.");
    }

    // Optionally, a method to check the status of pending products
    public void printPendingProducts() {
        System.out.println("Pending Products: ");
        for (Product product : pendingProducts) {
            System.out.println(product);
        }
    }
}
