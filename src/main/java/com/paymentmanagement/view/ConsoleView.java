package com.paymentmanagement.view;

import com.paymentmanagement.model.AgentType;
import com.paymentmanagement.model.LoginSession;
import com.paymentmanagement.service.*;
import com.paymentmanagement.view.menu.DirectorMenu;
import com.paymentmanagement.view.menu.EmployeeMenu;
import com.paymentmanagement.view.menu.ManagerMenu;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleView extends BaseMenu {
    private final AuthService authService;
    private final AgentService agentService;
    private final PaymentService paymentService;
    private final DepartmentService departmentService;
    private final StatisticsService statisticsService;

    public ConsoleView(AuthService authService, MenuService menuService, Scanner scanner, AgentService agentService, AuthService authService1, PaymentService paymentService, DepartmentService departmentService, StatisticsService statisticsService) {
        super(authService, menuService, scanner);
        this.agentService = agentService;
        this.authService = authService1;
        this.paymentService = paymentService;
        this.departmentService = departmentService;
        this.statisticsService = statisticsService;
    }


    @Override
    protected List<MenuItem> getMenuItems() {
        return Arrays.asList(
                new MenuItem(1, "Login"),
                new MenuItem(0, "Exit")
        );
    }

    @Override
    protected String getMenuTitle() {
        return "Payment Management System - Main menu";
    }

    public void show() {
        menuService.displayHeader("Welcome to Payment management system");
        runMenu();
    }

    @Override
    protected void handleChoice(int choice) {
        switch (choice) {
            case 1:
                handleLogin();
                break;
            default:
                menuService.showError("Invalid choice");
                break;
        }
    }

    private void handleLogin() {
        try {
            String email = menuService.readString("Email");
            String password = menuService.readString("password");

            LoginSession currentUser = authService.login(email, password);

            if(currentUser != null) {
                menuService.showSuccess("Welcome: " + currentUser.getUserName());

                AgentType currentUserType = currentUser.getCurrentLoggedAgent().getAgentType();
                if(currentUserType == AgentType.DIRECTOR) {
                    DirectorMenu directorMenu = new DirectorMenu(authService, menuService, scanner, agentService, departmentService, statisticsService);
                    directorMenu.show();
                } else if(currentUserType == AgentType.MANAGER) {
                    ManagerMenu managerMenu = new ManagerMenu(authService, menuService, scanner, agentService, paymentService, statisticsService);
                    managerMenu.show();
                } else if(currentUserType == AgentType.EMPLOYEE) {
                    EmployeeMenu employeeMenu = new EmployeeMenu(authService, menuService, scanner, paymentService);
                    employeeMenu.show();
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
