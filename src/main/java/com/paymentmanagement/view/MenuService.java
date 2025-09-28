package com.paymentmanagement.view;

import java.util.List;
import java.util.Scanner;

public class MenuService {
    private final Scanner scanner;

    public MenuService(Scanner scanner) {
        this.scanner = scanner;
    }

    public int displayMenuAndGetChoice(String title, List<MenuItem> menuItems) {
        displayHeader(title);
        for(MenuItem item: menuItems) {
            System.out.println(item);
        }

        System.out.println("-".repeat(50));
        return readInt("Enter your choice");
    }

    public String readString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    public int readInt(String prompt) {
        System.out.println(prompt + ": ");
        return scanner.nextInt();
    }

    public void displayHeader(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(centerText(title, 60));
        System.out.println("=".repeat(60));
    }


    public String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }
}
