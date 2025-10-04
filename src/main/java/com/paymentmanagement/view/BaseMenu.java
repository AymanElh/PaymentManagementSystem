package com.paymentmanagement.view;

import com.paymentmanagement.service.AuthService;

import java.util.List;
import java.util.Scanner;

public abstract class BaseMenu {
    protected final MenuService menuService;
    protected final Scanner scanner;
    protected final AuthService authService;

    public BaseMenu(AuthService authService, MenuService menuService, Scanner scanner) {
        this.authService = authService;
        this.menuService = menuService;
        this.scanner = scanner;
    }

    public abstract void show();
    protected abstract List<MenuItem> getMenuItems();
    protected abstract String getMenuTitle();
    protected abstract void handleChoice(int choice);

    protected void runMenu() {
        while(true) {
            List<MenuItem> menuItems = getMenuItems();
            int choice = menuService.displayMenuAndGetChoice(getMenuTitle(), menuItems);

            if(choice == 0) {
                break;
            }

            handleChoice(choice);
        }
    }
}
