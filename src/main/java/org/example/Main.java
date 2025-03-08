package org.example;

import org.example.controller.PaginationController;
import org.example.controller.ProductController;
import org.example.modal.dao.ProductDaoImp;

public class Main {

    public static void main(String[] args) {
        // Initialize DAO and Controller
        ProductDaoImp productDao = new ProductDaoImp();
        ProductController productController = new ProductController(productDao);

        // Adding products
//        productController.addProduct(new Product("Product 1", 100.0, 10));
//        productController.addProduct(new Product("Product 2", 150.0, 15));
//        productController.addProduct(new Product("Product 3", 200.0, 20));

        // Print pending products
//        productController.printPendingProducts();

        // Save products to the database
//        productController.saveProducts();

        // Print pending products after saving
//        productController.printPendingProducts();

        // Optionally cancel pending products
//         productController.cancelPending();

        // Initialize Controller
        PaginationController paginationController = new PaginationController();

        // Display the first page
        paginationController.displayFirstPage();

        // Display the next page
//        paginationController.displayNextPage(1);  // Assuming the current page is 1

        // Display the previous page
//        paginationController.displayPreviousPage(2);  // Assuming the current page is 2

        // Display the last page
//        paginationController.displayLastPage();

        // Display a specific page
//        paginationController.displayPage(3);
    }
}
