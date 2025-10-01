package com.paymentmanagement.view;

import java.util.List;
import java.util.Scanner;

public class MenuService {
    private final Scanner scanner;

    public MenuService(Scanner scanner) {
        this.scanner = scanner;
    }

    public int displayMenuAndGetChoice(String title, List<MenuItem> menuItems) {
        displayTitle(title);
        for(MenuItem item: menuItems) {
            System.out.println(item);
        }

        System.out.println("-".repeat(50));
        return readInt("Enter your choice");
    }

    public String readString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.next();
    }

    public Double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                return Double.parseDouble(scanner.next().trim());
            } catch (NumberFormatException e) {
                showError("Please enter a valid decimal number");
            }
        }
    }

    public Integer readInt(String prompt) {
        while(true) {
            try {
                System.out.print(prompt + ": ");
                return Integer.parseInt(scanner.next().trim());
            } catch (NumberFormatException e) {
                showError("Please enter a valid number");
            }
        }
    }

    public void showSuccess(String message) {
        System.out.println("Success: " + message);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public void displayHeader(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(centerText(title, 60));
        System.out.println("=".repeat(60));
    }

    public void displayTitle(String title) {
        System.out.println("----------   " + title + "   --------------");
    }

    public void displaySeperator() {
        System.out.println("-".repeat(100));
    }

    public String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }
}
