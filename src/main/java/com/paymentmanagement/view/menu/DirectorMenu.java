package com.paymentmanagement.view.menu;

import com.paymentmanagement.model.Agent;
import com.paymentmanagement.model.Department;
import com.paymentmanagement.service.AgentService;
import com.paymentmanagement.service.AuthService;
import com.paymentmanagement.service.DepartmentService;
import com.paymentmanagement.view.BaseMenu;
import com.paymentmanagement.view.MenuItem;
import com.paymentmanagement.view.MenuService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DirectorMenu extends BaseMenu {
    private final DepartmentService departmentService;
    private final AgentService agentService;
    public DirectorMenu(AuthService authService, MenuService menuService, Scanner scanner, AgentService agentService, DepartmentService departmentService) {
        super(authService, menuService, scanner);
        this.agentService = agentService;
        this.departmentService = departmentService;
    }

    @Override
    protected List<MenuItem> getMenuItems() {
        return Arrays.asList(
                new MenuItem(1, "Create department"),
                new MenuItem(2, "Delete department"),
                new MenuItem(3, "View all departments"),
                new MenuItem(4, "Create manager"),
                new MenuItem(5, "Update manager"),
                new MenuItem(6, "View all managers"),
                new MenuItem(0, "Exit")
        );
    }

    @Override
    protected String getMenuTitle() {
        String userName = authService.getCurrentUser().getUserName();
        return "Payment management system  -  Director " + userName;
    }

    @Override
    public void show() {
        menuService.displayHeader("Director actions");
        runMenu();
    }

    public void handleChoice(int choice) {
        switch (choice) {
            case 1:
                createNewDepartment();
                break;
            case 2:
                deleteDepartment();
                break;
            case 3:
                viewAllDepartments();
                break;
            case 4:
                createNewManager();
                break;
            case 5:
                //
            case 6:
                //
            default:
                menuService.showSuccess("Invalid choice try again");
        }
    }

    private void createNewManager() {
        menuService.displayHeader("Enter manager info");
        String firstName = menuService.readString("First Name");
        String lastName = menuService.readString("Last Name");
        String email = menuService.readString("Email");
        String password = menuService.readString("Password");
        String phone = menuService.readString("Phone");
        String startDate = menuService.readString("Start date");
        double salary = menuService.readDouble("Salary");

        try {
            Date date = (new SimpleDateFormat("YYYY-mm-dd")).parse(startDate);
            Department dep = chooseDepartmentForManager();
            Agent agent = new Agent(firstName, lastName, email, password, phone, date, salary, dep);
            agentService.addManager(agent);
        }catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error in adding manager: " + e.getMessage());
            return;
        }

    }

    private Department chooseDepartmentForManager() {
        List<Department> departments = departmentService.getAllDepartments();
        if (departments.isEmpty()) {
            System.out.println("There no department yet");
            return null;
        }

        menuService.displayHeader(" -- Choose a department --");

        List<MenuItem> departmentMenuItems = new ArrayList<>();
        for (int i = 0; i < departments.size(); i++) {
            Department dep = departments.get(i);
            departmentMenuItems.add(new MenuItem(i + 1, dep.getName()));
        }

        int deptChoice = menuService.displayMenuAndGetChoice("Enter you choice", departmentMenuItems);

        if (deptChoice < 1 || deptChoice > departments.size()) {
            System.out.println("Invalid department selection.");
            return null;
        }

        Department dep = departments.get(deptChoice - 1);
        return dep;
    }


    private void createNewDepartment() {
        menuService.displayHeader("Enter department info");
        String name = menuService.readString("Name");
        String description = menuService.readString("Description");
        Department created = departmentService.addDepartment(new Department(name, description));
        if (created == null) {
            menuService.showError("Department '" + name + "' already exists or could not be created.");
        } else {
            menuService.showSuccess("Department '" + created.getName() + "' created successfully.");
        }
    }

    private void deleteDepartment() {
        Department department = chooseDepartmentForManager();
        if (department == null) {
            menuService.showError("No department selected or available.");
            return;
        }

        Department deleted = departmentService.deleteDepartment(department.getId());
        if (deleted == null) {
            menuService.showError("Cannot delete department. It may have assigned agents (manager or employees) or does not exist.");
        } else {
            menuService.showSuccess("Department '" + deleted.getName() + "' deleted successfully.");
        }
    }

    private void viewAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        for(Department department: departments) {
            System.out.println(department);
        }
    }
}
