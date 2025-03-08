package org.example.utils;

import org.example.exception.InputValidationException;

import java.util.List;
import java.util.Scanner;

public class InputValidator {

    private static final Scanner sc = new Scanner(System.in);
    private static final int DEFAULT_ATTEMPTS = 5;
    private  static final List<String> DEFAULT_MENUOPTIONS = List.of();


    public static <T> String getValidatedInput(String label, String regex) throws InputValidationException {
        return getValidatedInput(label, DEFAULT_MENUOPTIONS, regex, DEFAULT_ATTEMPTS);
    }

    public static <T> String getValidatedInput(String label, List<T> menuOptions, String regex, int attempts) throws InputValidationException {
        String input;

        displayMenuOptions(menuOptions);

        for (int count = 1; count <= attempts; count++) {
            System.out.print(label);
            input = sc.nextLine().trim();

            if (input.matches(regex)) {
                return input;
            }
            System.out.println("Invalid input. Please try again.");
        }

        throw new InputValidationException("Maximum attempts reached. Input validation failed.");
    }

    public static <T> void displayMenuOptions(List<T> menuOptions) {
        for (T option : menuOptions) {
            System.out.println(option.toString());
        }
    }
}
