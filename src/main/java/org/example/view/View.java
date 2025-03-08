package org.example.view;

import org.example.controller.PaginationController;
import org.example.controller.ProductController;
import org.example.modal.dao.ProductDaoImp;
import org.example.modal.entity.Product;

import java.util.Scanner;

public class View {
    // Initialize DAO and Controllers
    private ProductDaoImp productDao = new ProductDaoImp();
    private ProductController productController = new ProductController(productDao);
    private PaginationController paginationController = new PaginationController();

    // Constructor
    public View() {
        // You can call a method here if necessary for initialization
    }

    // Method to handle different actions based on user input
    public void handleAction(int choice) {
        switch (choice) {
            case 1:
                addProducts();                // Add products
                break;
            case 2:
                printPendingProducts();       // Print pending products
                break;
            case 3:
                saveProducts();               // Save products to database
                break;
            case 4:
                displayFirstPage();           // Display the first page
                break;
            case 5:
                displayNextPage(1);           // Display next page (assuming page 1)
                break;
            case 6:
                displayPreviousPage(2);       // Display previous page (assuming page 2)
                break;
            case 7:
                displayLastPage();            // Display the last page
                break;
            case 8:
                getAllProducts();             // Get all products
                break;
            case 9:
                getProductById(17);           // Get a product by ID
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    // Add products
    public void addProducts() {
        productController.addProduct(new Product("Product 1", 100.0, 10));
        productController.addProduct(new Product("Product 2", 150.0, 15));
        productController.addProduct(new Product("Product 3", 200.0, 20));
        System.out.println("Products added.");
    }

    // Print pending products
    public void printPendingProducts() {
        productController.printPendingProducts();
    }

    // Save products to the database
    public void saveProducts() {
        productController.saveProducts();
        System.out.println("Products saved to the database.");
    }

    // Display first page
    public void displayFirstPage() {
        paginationController.displayFirstPage();
    }

    // Display next page
    public void displayNextPage(int currentPage) {
        paginationController.displayNextPage(currentPage);  // Assuming the current page is passed
    }

    // Display previous page
    public void displayPreviousPage(int currentPage) {
        paginationController.displayPreviousPage(currentPage);  // Assuming the current page is passed
    }

    // Display last page
    public void displayLastPage() {
        paginationController.displayLastPage();
    }

    // Get all products
    public void getAllProducts() {
        productController.getAllProducts();
    }

    // Get product by ID
    public void getProductById(int id) {
        productController.getProductById(id);
    }

    // Main method to start the program and interact with the user
    public static void main(String[] args) {
        // Instantiate View class
        View view = new View();

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Menu for the user to choose an action
        System.out.println("Select an action:");
        System.out.println("1. Add products");
        System.out.println("2. Print pending products");
        System.out.println("3. Save products");
        System.out.println("4. Display first page");
        System.out.println("5. Display next page");
        System.out.println("6. Display previous page");
        System.out.println("7. Display last page");
        System.out.println("8. Get all products");
        System.out.println("9. Get product by ID");
        System.out.print("Enter your choice: ");

        // Get the user's choice
        int choice = scanner.nextInt();

        // Handle the user's choice using the switch-case
        view.handleAction(choice);

        // Close the scanner
        scanner.close();
    }
}
