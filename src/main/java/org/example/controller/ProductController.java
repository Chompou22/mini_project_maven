package org.example.controller;

import org.example.modal.dao.ProductDaoImp;
import org.example.modal.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductController {

    private final Scanner scanner = new Scanner(System.in);

    private final ProductDaoImp productDao;
    private final List<Product> pendingProducts;
    private final List<Product> pendingProductsToUpdate; // List for holding updated products
    private boolean saveRequested;

    public ProductController(ProductDaoImp productDao) {
        this.productDao = productDao;
        this.pendingProducts = new ArrayList<>();
        this.pendingProductsToUpdate = new ArrayList<>();
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
            productDao.save(pendingProducts); // Save added products
            productDao.saveUpdates(pendingProductsToUpdate); // Save updated products
            System.out.println("Products saved to the database.");
        }
    }

    // Cancel pending products
    public void cancelPending() {
        pendingProducts.clear();
        pendingProductsToUpdate.clear(); // Clear pending updates as well
        saveRequested = false;
        System.out.println("Cancelled pending products.");
    }

    // Print pending products
    public void printPendingProducts() {
        System.out.println("Pending Products: ");
        displayProducts(pendingProducts);
    }

    // Print pending product updates
    public void printPendingUpdates() {
        System.out.println("Pending Product Updates: ");
        displayProducts(pendingProductsToUpdate);
    }

    // Get all products from the database
    public void getAllProducts() {
        List<Product> products = productDao.getAllProducts();
        System.out.println("Retrieved all products from the database:");
        displayProducts(products);
    }

    // Get a specific product by ID and display it
    public void getProductById(int id) {
        Product product = productDao.getProductById(id);
        if (product != null) {
            System.out.println("Retrieved product:");
            displayProduct(product);
        } else {
            System.out.println("Product with ID " + id + " not found.");
        }
    }

    public void searchProduct() {
        System.out.print("Enter product name to search: ");
        String name = scanner.nextLine();

        List<Product> foundProducts = productDao.searchProductByName(name);
        if (foundProducts.isEmpty()) {
            System.out.println("No products found with the name: " + name);
        } else {
            System.out.println("Found products:");
            for (Product product : foundProducts) {
                System.out.println(product);
            }
        }
    }


    // Function to display a single product
    private void displayProduct(Product product) {
        Product.printTableHeader();
        System.out.println(product);
    }

    // Function to display a list of products
    private void displayProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            Product.printTableHeader();
            for (Product product : products) {
                displayProduct(product);
            }
        }
    }

    // Update product (add to pending update list)
    public void updateProduct() {
        System.out.print("Enter Product ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product existingProduct = productDao.getProductById(id);
        if (existingProduct == null) {
            System.out.println("Product not found with ID: " + id);
            return;
        }

        // Collect new product details
        System.out.print("Enter new name (current: " + existingProduct.getName() + "): ");
        String name = scanner.nextLine();
        name = name.isEmpty() ? existingProduct.getName() : name;

        System.out.print("Enter new unit price (current: " + existingProduct.getUnitPrice() + "): ");
        String unitPriceInput = scanner.nextLine();
        double unitPrice = unitPriceInput.isEmpty() ? existingProduct.getUnitPrice() : Double.parseDouble(unitPriceInput);

        System.out.print("Enter new quantity (current: " + existingProduct.getQuantity() + "): ");
        String quantityInput = scanner.nextLine();
        int quantity = quantityInput.isEmpty() ? existingProduct.getQuantity() : Integer.parseInt(quantityInput);

        // Create an updated product
        Product updatedProduct = new Product(id, name, unitPrice, quantity, existingProduct.getImportedDate());

        // Add to pending updates list
        pendingProductsToUpdate.add(updatedProduct);
        System.out.println("Product added to pending updates list.");
    }
}
