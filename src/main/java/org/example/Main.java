package org.example;

import org.example.view.View;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create an instance of the View class
        View view = new View();

        // Scanner to get user input
        Scanner scanner = new Scanner(System.in);

        // Display menu to user
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

        // Call the handleAction method in the View class to process the user's choice
        view.handleAction(choice);

        // Close the scanner
        scanner.close();
    }
}
