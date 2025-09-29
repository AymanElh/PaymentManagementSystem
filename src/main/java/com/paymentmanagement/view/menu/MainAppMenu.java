package com.paymentmanagement.view.menu;

import com.paymentmanagement.service.AgentService;
import com.paymentmanagement.service.AuthService;
import com.paymentmanagement.service.DepartmentService;
import com.paymentmanagement.service.PaymentService;
import com.paymentmanagement.view.MenuItem;
import com.paymentmanagement.view.MenuService;

import java.util.Arrays;
import java.util.List;

public class MainAppMenu {
    private final AuthService authService;
    private final AgentService agentService;
    private final DepartmentService departmentService;
    private final PaymentService paymentService;
    private final MenuService menuService;

    public MainAppMenu(AgentService agentService, AuthService authService, DepartmentService departmentService, PaymentService paymentService, MenuService menuService) {
        this.agentService = agentService;
        this.authService = authService;
        this.departmentService = departmentService;
        this.paymentService = paymentService;
        this.menuService = menuService;
    }

    public List<MenuItem> getMenuItems() {
        return Arrays.asList(
                //
        );
    }

    public String getMenuTitle() {
        String userName = authService.getCurrentUser().getUserName();
        return "Welcome - " + userName;
    }

    public void show() {
        // run menu
        while(true) {
            List<MenuItem> menuItems = getMenuItems();
            int choice = menuService.displayMenuAndGetChoice(getMenuTitle(), menuItems);

            // handle choice
            System.out.println("Agent menu");
        }
    }
}
