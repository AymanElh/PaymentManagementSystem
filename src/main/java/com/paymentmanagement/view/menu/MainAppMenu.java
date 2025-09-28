package com.paymentmanagement.view.menu;

import com.paymentmanagement.service.AgentService;
import com.paymentmanagement.service.AuthService;
import com.paymentmanagement.service.DepartmentService;
import com.paymentmanagement.service.PaymentService;
import com.paymentmanagement.view.MenuItem;

import java.util.Arrays;
import java.util.List;

public class MainAppMenu {
    private final AuthService authService;
    private final AgentService agentService;
    private final DepartmentService departmentService;
    private final PaymentService paymentService;

    public MainAppMenu(AgentService agentService, AuthService authService, DepartmentService departmentService, PaymentService paymentService) {
        this.agentService = agentService;
        this.authService = authService;
        this.departmentService = departmentService;
        this.paymentService = paymentService;
    }

    public List<MenuItem> getMenuItems() {
        return Arrays.asList(
                new MenuItem(1, "Agent Management"),
                new MenuItem(2, "Payment Management"),
                new MenuItem(3, "Department Management"),
                new MenuItem(4, "Reports"),
                new MenuItem(5, "Settings"),
                new MenuItem(0, "Logout")
        );
    }

    public String getMenuTitle() {
        String userName =
    }
}
