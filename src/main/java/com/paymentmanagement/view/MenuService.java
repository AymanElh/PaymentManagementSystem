package com.paymentmanagement.view;

import java.util.List;
import java.util.Scanner;

public class MenuService {
    private final Scanner scanner;

    // ANSI Color Codes
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String PURPLE = "\u001B[35m";
    private static final String WHITE = "\u001B[37m";

    // Box Drawing Characters
    private static final String TOP_LEFT = "╔";
    private static final String TOP_RIGHT = "╗";
    private static final String BOTTOM_LEFT = "╚";
    private static final String BOTTOM_RIGHT = "╝";
    private static final String HORIZONTAL = "═";
    private static final String VERTICAL = "║";

    public MenuService(Scanner scanner) {
        this.scanner = scanner;
    }

    public int displayMenuAndGetChoice(String title, List<MenuItem> menuItems) {
        displayTitle(title);
        System.out.println();
        for(MenuItem item: menuItems) {
            System.out.println("  " + CYAN + item + RESET);
        }
        System.out.println("\n" + BLUE + "─".repeat(60) + RESET);
        return readInt("Enter your choice");
    }

    public String readString(String prompt) {
        System.out.print(YELLOW + "➤ " + prompt + ": " + RESET);
        return scanner.next();
    }

    public Double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(YELLOW + "➤ " + prompt + ": " + RESET);
                return Double.parseDouble(scanner.next().trim());
            } catch (NumberFormatException e) {
                showError("Please enter a valid decimal number");
            }
        }
    }

    public Integer readInt(String prompt) {
        while(true) {
            try {
                System.out.print(YELLOW + "➤ " + prompt + ": " + RESET);
                return Integer.parseInt(scanner.next().trim());
            } catch (NumberFormatException e) {
                showError("Please enter a valid number");
            }
        }
    }

    public void showSuccess(String message) {
        System.out.println("\n" + GREEN + "✓ Success: " + BOLD + message + RESET + "\n");
    }

    public void showError(String message) {
        System.out.println("\n" + RED + "✗ Error: " + BOLD + message + RESET + "\n");
    }

    public void displayHeader(String title) {
        int width = 70;
        System.out.println("\n" + CYAN + BOLD);
        System.out.println(TOP_LEFT + HORIZONTAL.repeat(width - 2) + TOP_RIGHT);
        System.out.println(VERTICAL + centerText(title, width - 2) + VERTICAL);
        System.out.println(BOTTOM_LEFT + HORIZONTAL.repeat(width - 2) + BOTTOM_RIGHT);
        System.out.println(RESET);
    }

    public void displayTitle(String title) {
        int width = 60;
        System.out.println("\n" + BLUE + BOLD + "┌" + "─".repeat(width - 2) + "┐");
        System.out.println("│" + centerText(title.toUpperCase(), width - 2) + "│");
        System.out.println("└" + "─".repeat(width - 2) + "┘" + RESET);
    }

    public void displaySeperator() {
        System.out.println(BLUE + "─".repeat(80) + RESET);
    }

    public String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        int rightPadding = width - text.length() - padding;
        return " ".repeat(Math.max(0, padding)) + text + " ".repeat(Math.max(0, rightPadding));
    }
}
