package com.paymentmanagement.view;

import com.paymentmanagement.service.AgentService;
import com.paymentmanagement.service.AuthService;
import com.paymentmanagement.service.DepartmentService;
import com.paymentmanagement.service.PaymentService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private final AuthService authService;
    private final AgentService agentService;
    private final DepartmentService departmentService;
    private final PaymentService paymentService;
    private final Scanner scanner;
    private final MenuService menuService;

    public ConsoleView(AgentService agentService, AuthService authService, DepartmentService departmentService, PaymentService paymentService, Scanner scanner, MenuService menuService) {
        this.agentService = agentService;
        this.authService = authService;
        this.departmentService = departmentService;
        this.paymentService = paymentService;
        this.scanner = scanner;
        this.menuService = menuService;
    }

    public List<MenuItem> getMenuItems() {
        return Arrays.asList(
                new MenuItem(1, "Login"),
                new MenuItem(2, "Exit")
        );
    }

    public void show() {
        menuService.displayHeader("Welcome to Payment management system");
        while(true) {
            List<MenuItem> menuItems = getMenuItems();
            int choice = menuService.displayMenuAndGetChoice("Payment management system - Main Menu", menuItems);

            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Error: Invalid choice");
                    break;
            }
        }
    }

    private void handleLogin() {
        try {
            String email = menuService.readString("Email");
            String password = menuService.readString("password");

            if(authService.login(email, password)) {
                System.out.println("Login successfully");
            } else {
                System.out.println("Invalid email or password");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
