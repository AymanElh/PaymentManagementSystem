package com.paymentmanagement.view.menu;

import com.paymentmanagement.model.Payment;
import com.paymentmanagement.service.AgentService;
import com.paymentmanagement.service.AuthService;
import com.paymentmanagement.service.PaymentService;
import com.paymentmanagement.view.BaseMenu;
import com.paymentmanagement.view.MenuItem;
import com.paymentmanagement.view.MenuService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class EmployeeMenu extends BaseMenu {
    private final AgentService agentService;
    private final PaymentService paymentService;

    public EmployeeMenu(AuthService authService, MenuService menuService, Scanner scanner, AgentService agentService, PaymentService paymentService) {
        super(authService, menuService, scanner);
        this.agentService = agentService;
        this.paymentService = paymentService;
    }

    @Override
    protected List<MenuItem> getMenuItems() {
        return Arrays.asList(
                new MenuItem(1, "View payment history"),
                new MenuItem(2, "Calculate total of payments"),
                new MenuItem(3, "Find and sort payments")
        );
    }

    @Override
    public void show() {
        menuService.displayHeader("Employee actions");
        runMenu();
    }

    @Override
    protected String getMenuTitle() {
        String userName = authService.getCurrentUser().getUserName();
        return userName;
    }

    @Override
    protected void handleChoice(int choice) {
        switch (choice) {
            case 1:

        }
    }

}
