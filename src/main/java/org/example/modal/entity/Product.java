package org.example.modal.entity;

import java.time.LocalDate;

public class Product {
    private static int counter = 0;
    private int id;
    private String name;
    private double unitPrice;
    private int quantity;
    private LocalDate importedDate;

    // Constructor for new products (without an ID)
    public Product(String name, double unitPrice, int quantity) {
        this.id = ++counter; // Temporary ID (until stored in DB)
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.importedDate = LocalDate.now();
    }

    public Product(int id, String name, double unitPrice, int quantity) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    // Constructor for products retrieved from the database (with an ID)
    public Product(int id, String name, double unitPrice, int quantity, LocalDate importedDate) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.importedDate = importedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id; // Allow setting the ID from the database
    }

    public String getName() {
        return name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getImportedDate() {
        return importedDate;
    }

    // Method to print the table header
    public static void printTableHeader() {
        System.out.println("+-------+----------------------+------------+------------+-----------------+");
        System.out.println("|  ID   | Name                 | Unit Price | Quantity   | Imported Date   |");
        System.out.println("+-------+----------------------+------------+------------+-----------------+");
    }

    @Override
    public String toString() {
        return String.format("| %-5d | %-20s | %-10.2f | %-10d | %-15s |",
                id, name, unitPrice, quantity, importedDate);
    }


}
