package com.paymentmanagement.view.menu;

import com.paymentmanagement.model.Agent;
import com.paymentmanagement.model.Department;
import com.paymentmanagement.model.PaymentType;
import com.paymentmanagement.service.AgentService;
import com.paymentmanagement.service.AuthService;
import com.paymentmanagement.service.DepartmentService;
import com.paymentmanagement.service.StatisticsService;
import com.paymentmanagement.view.BaseMenu;
import com.paymentmanagement.view.MenuItem;
import com.paymentmanagement.view.MenuService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DirectorMenu extends BaseMenu {
    private final DepartmentService departmentService;
    private final AgentService agentService;
    private final StatisticsService statisticsService;
    public DirectorMenu(AuthService authService, MenuService menuService, Scanner scanner, AgentService agentService, DepartmentService departmentService, StatisticsService statisticsService) {
        super(authService, menuService, scanner);
        this.agentService = agentService;
        this.departmentService = departmentService;
        this.statisticsService = statisticsService;
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
                new MenuItem(7, "Distribution of payments per type"),
                new MenuItem(8, "Update department manager")
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
                getAllManagers();
                break;
            case 7:
                getDistributionByPaymentType();
                break;
            case 8:
                updateDepartmentManager();
                break;
            default:
                menuService.showSuccess("Invalid choice try again");
        }
    }

    private void createNewManager() {
        menuService.displayHeader("Enter Manager Information");
        String firstName = menuService.readString("First Name");
        String lastName = menuService.readString("Last Name");
        String email = menuService.readString("Email");
        String password = menuService.readString("Password");
        String phone = menuService.readString("Phone");
        String startDate = menuService.readString("Start date (YYYY-MM-dd)");
        double salary = menuService.readDouble("Salary");

        try {
            Date date = (new SimpleDateFormat("yyyy-MM-dd")).parse(startDate);
            Department dep = chooseDepartmentForManager();
            if (dep == null) {
                menuService.showError("Department selection cancelled or invalid.");
                return;
            }
            Agent agent = new Agent(firstName, lastName, email, password, phone, date, salary, dep);
            agentService.addManager(agent);
            menuService.showSuccess("Manager '" + firstName + " " + lastName + "' added successfully!");
        }catch (ParseException e) {
            menuService.showError("Invalid date format. Please use YYYY-MM-dd format.");
        } catch (Exception e) {
            menuService.showError("Error adding manager: " + e.getMessage());
        }
    }

    private Department chooseDepartmentForManager() {
        List<Department> departments = departmentService.getAllDepartments();
        if (departments.isEmpty()) {
            menuService.showError("No departments available yet.");
            return null;
        }

        menuService.displayTitle("Select Department");

        List<MenuItem> departmentMenuItems = new ArrayList<>();
        for (int i = 0; i < departments.size(); i++) {
            Department dep = departments.get(i);
            departmentMenuItems.add(new MenuItem(i + 1, dep.getName()));
        }

        int deptChoice = menuService.displayMenuAndGetChoice("Choose department", departmentMenuItems);

        if (deptChoice < 1 || deptChoice > departments.size()) {
            menuService.showError("Invalid department selection.");
            return null;
        }

        Department dep = departments.get(deptChoice - 1);
        return dep;
    }


    private void createNewDepartment() {
        menuService.displayHeader("Create New Department");
        String name = menuService.readString("Department Name");
        String description = menuService.readString("Description");
        Department created = departmentService.addDepartment(new Department(name, description));
        if (created == null) {
            menuService.showError("Department '" + name + "' already exists or could not be created.");
        } else {
            menuService.showSuccess("Department '" + created.getName() + "' created successfully!");
        }
    }

    private void deleteDepartment() {
        menuService.displayTitle("Delete Department");
        Department department = chooseDepartmentForManager();
        if (department == null) {
            menuService.showError("No department selected or available.");
            return;
        }

        Department deleted = departmentService.deleteDepartment(department.getId());
        if (deleted == null) {
            menuService.showError("Cannot delete department. It may have assigned agents or does not exist.");
        } else {
            menuService.showSuccess("Department '" + deleted.getName() + "' deleted successfully!");
        }
    }

    private void viewAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();

        if (departments.isEmpty()) {
            menuService.showError("No departments found.");
            return;
        }

        menuService.displayHeader("All Departments");
        System.out.printf("  %-5s %-25s %-40s%n", "ID", "Name", "Description");
        menuService.displaySeperator();

        for(Department department: departments) {
            System.out.printf("  %-5d %-25s %-40s%n",
                department.getId(),
                department.getName(),
                department.getDescription());
        }

        System.out.println();
    }

    private void updateDepartmentManager() {
        menuService.displayHeader("Update Department Manager");
        menuService.displayTitle("Step 1: Choose Department");
        Department department = chooseDepartmentForManager();

        if (department == null) {
            menuService.showError("Department selection cancelled.");
            return;
        }

        List<Agent> agentsOnThisDep = agentService.getEmployeesOnDepartment(department.getId());

        if (agentsOnThisDep.isEmpty()) {
            menuService.showError("No employees found in department '" + department.getName() + "'.");
            return;
        }

        menuService.displayTitle("Step 2: Select New Manager");

        List<MenuItem> menuItems = new ArrayList<>();
        for(int i = 0; i < agentsOnThisDep.size(); i++) {
            Agent agent = agentsOnThisDep.get(i);
            menuItems.add(new MenuItem(i+1, agent.getFirstName() + " " + agent.getLastName() + " (ID: " + agent.getId() + ")"));
        }

        int choice = menuService.displayMenuAndGetChoice("Choose the employee to promote", menuItems);

        if (choice < 1 || choice > agentsOnThisDep.size()) {
            menuService.showError("Invalid employee selection.");
            return;
        }

        Agent manager = agentService.updateDepartmentManager(agentsOnThisDep.get(choice - 1), department.getId());
        menuService.showSuccess("Manager updated successfully!\n  👤 New Manager: " + manager.getFirstName() + " " + manager.getLastName());
    }

    private void getAllManagers() {
        List<Agent> agents = agentService.getAllManagers();

        if (agents.isEmpty()) {
            menuService.showError("No managers found.");
            return;
        }

        menuService.displayHeader("All Managers");
        System.out.printf("  %-5s %-20s %-20s %-25s%n", "ID", "First Name", "Last Name", "Department");
        menuService.displaySeperator();

        for (Agent agent : agents) {
            String departmentName = (agent.getDepartment() != null) ? agent.getDepartment().getName() : "No Department";
            System.out.printf("  %-5d %-20s %-20s %-25s%n",
                agent.getId(),
                agent.getFirstName(),
                agent.getLastName(),
                departmentName);
        }
        System.out.println();
    }

    private void getDistributionByPaymentType() {
        Map<PaymentType, Double> paymentDistribution = statisticsService.distibutionOfPaymentByType();

        if (paymentDistribution.isEmpty()) {
            menuService.showError("No payment data available.");
            return;
        }

        menuService.displayHeader("Payment Distribution by Type");
        menuService.displaySeperator();

        for(PaymentType type: paymentDistribution.keySet()) {
            double percentage = paymentDistribution.get(type);
            String bar = "█".repeat((int)(percentage / 2)); // Simple bar chart
            System.out.printf("  %-15s : %6.2f%% %s%n", type.name(), percentage, bar);
        }

        System.out.println();
        menuService.displaySeperator();
    }
}
