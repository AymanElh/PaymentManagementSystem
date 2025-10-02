package com.paymentmanagement.view.menu;

import com.paymentmanagement.model.Agent;
import com.paymentmanagement.model.Department;
import com.paymentmanagement.model.Payment;
import com.paymentmanagement.model.PaymentType;
import com.paymentmanagement.service.AgentService;
import com.paymentmanagement.service.AuthService;
import com.paymentmanagement.service.PaymentService;
import com.paymentmanagement.service.StatisticsService;
import com.paymentmanagement.view.BaseMenu;
import com.paymentmanagement.view.MenuItem;
import com.paymentmanagement.view.MenuService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ManagerMenu extends BaseMenu {
    private final AgentService agentService;
    private final PaymentService paymentService;
    private final StatisticsService statisticsService;

    public ManagerMenu(AuthService authService, MenuService menuService, Scanner scanner,
                       AgentService agentService, PaymentService paymentService, StatisticsService statisticsService) {
        super(authService, menuService, scanner);
        this.agentService = agentService;
        this.paymentService = paymentService;
        this.statisticsService = statisticsService;
    }

    @Override
    protected List<MenuItem> getMenuItems() {
        return Arrays.asList(
                new MenuItem(1, "Create employee in my department"),
                new MenuItem(2, "Remove (deactivate) employee from my department"),
                new MenuItem(3, "List my employees"),
                new MenuItem(4, "Make a payment to an employee"),
                new MenuItem(5, "Rank employees by payment"),
                new MenuItem(6, "Get total payment in department"),
                new MenuItem(7, "Get average payment on department")
        );
    }

    @Override
    protected String getMenuTitle() {
        String userName = authService.getCurrentUser().getUserName();
        return "Payment management system  -  Manager " + userName;
    }

    @Override
    public void show() {
        menuService.displayHeader("Manager actions");
        runMenu();
    }

    @Override
    protected void handleChoice(int choice) {
        switch (choice) {
            case 1:
                createEmployee();
                break;
            case 2:
                removeEmployee();
                break;
            case 3:
                listMyEmployees();
                break;
            case 4:
                makePaymentToEmployee();
                break;
            case 5:
                rankEmployeesOnDep();
                break;
            case 6:
                totalPaymentsOnDepartment();
                break;
            case 7:
                averagePaymentsInDep();
                break;
            default:
                menuService.showError("Invalid choice, try again");
        }
    }

    private Department getMyDepartment() {
        Agent current = authService.getCurrentUser().getCurrentLoggedAgent();
        Department dep = current.getDepartment();
        if (dep == null) {
            menuService.showError("You are not assigned to any department.");
        }
        return dep;
    }

    private void createEmployee() {
        Department myDep = getMyDepartment();
        if (myDep == null) return;

        menuService.displayHeader("Enter new employee info");
        String firstName = menuService.readString("First Name");
        String lastName = menuService.readString("Last Name");
        String email = menuService.readString("Email");
        String password = menuService.readString("Password");
        String phone = menuService.readString("Phone");
        String startDate = menuService.readString("Start date");
        double salary = menuService.readDouble("Salary");

        try {
            Date date = new SimpleDateFormat("YYYY-mm-dd").parse(startDate);

            Agent employee = new Agent(firstName, lastName, email, password, phone, date, salary, myDep);
            Agent created = agentService.addEmployee(employee);
            if (created != null) {
                menuService.showSuccess("Employee '" + created.getFirstName() + " " + created.getLastName() + "' created in department '" + myDep.getName() + "'.");
            } else {
                menuService.showError("Could not create employee. Please try again.");
            }
        } catch (ParseException e) {
            System.out.println("Error in parsing date: " + e.getMessage());
            return;
        } catch (Exception e) {
            menuService.showError("Error creating employee: " + e.getMessage());
            return;
        }
    }

    private void removeEmployee() {
        Department myDep = getMyDepartment();
        if (myDep == null) return;
        System.out.println(myDep);
        List<Agent> employees = agentService.getEmployeesOnDepartment(myDep.getId());
        if (employees.isEmpty()) {
            menuService.showError("No employees in your department.");
            return;
        }

        int employeeId = chooseEmployee(employees);
        if (employeeId == -1) return;

        try {
            Agent deactivated = agentService.desactivateAgent(employeeId);
            if (deactivated != null) {
                menuService.showSuccess("Employee deactivated: " + deactivated.getFirstName() + " " + deactivated.getLastName());
            } else {
                menuService.showError("Failed to deactivate employee.");
            }
        } catch (Exception e) {
            menuService.showError("Error removing employee: " + e.getMessage());
        }
    }

    private void listMyEmployees() {
        Department myDep = getMyDepartment();
        if (myDep == null) return;
        List<Agent> employees = agentService.getEmployeesOnDepartment(myDep.getId());
        if (employees.isEmpty()) {
            menuService.showError("No employees in your department.");
            return;
        }
        menuService.displayHeader("-- My Employees --");
        for (Agent emp : employees) {
            String status = emp.getIsActive() ? "ACTIVE" : "INACTIVE";
            System.out.println(emp.getId() + ": " + emp.getFirstName() + " " + emp.getLastName() + " (" + status + ")");
        }
    }

    private void makePaymentToEmployee() {
        Department myDep = getMyDepartment();
        if (myDep == null) return;
        List<Agent> employees = agentService.getEmployeesOnDepartment(myDep.getId());
        if (employees.isEmpty()) {
            menuService.showError("No employees in your department.");
            return;
        }

        int employeeId = chooseEmployee(employees);
        if (employeeId == -1) return;

        // Choose payment type
        List<MenuItem> paymentTypes = Arrays.asList(
                new MenuItem(1, "Salary"),
                new MenuItem(2, "Prime"),
                new MenuItem(3, "Bonus")
        );
        int typeChoice = menuService.displayMenuAndGetChoice("Choose payment type", paymentTypes);
        // Build a lightweight Agent reference to attach to Payment
        Agent selected = employees.stream().filter(a -> a.getId() == employeeId).findFirst().orElse(null);

        double amount = 0.0;
        if (typeChoice == 1) {
            amount = selected.getSalary();

        } else if(typeChoice == 2) {
            amount = menuService.readDouble("Amount");
            if (amount > selected.getSalary()) return;
        }


        if (selected == null) {
            menuService.showError("Invalid employee selection.");
            return;
        }
        PaymentType type = typeChoice == 1 ? PaymentType.SALARY : (
                typeChoice == 2 ? PaymentType.PRIME : (
                        typeChoice == 3 ? PaymentType.PRIME : null
                )
        );
        Payment payment = new Payment(selected, amount, true, new java.util.Date(), type);
        System.out.println("payment: " + payment);
        try {
            switch (typeChoice) {
                case 1:
                    paymentService.addSalaryToAgent(payment);
                    menuService.showSuccess("Salary payment added successfully.");
                    break;
                case 2:
                    paymentService.addPrimeToAgent(payment);
                    menuService.showSuccess("Prime payment added successfully.");
                    break;
                case 3:
                    paymentService.addBonusToAgent(payment);
                    menuService.showSuccess("Bonus payment added successfully.");
                    break;
                default:
                    menuService.showError("Invalid payment type.");
            }
        } catch (Exception e) {
            menuService.showError("Error making payment: " + e.getMessage());
        }
    }

    private void rankEmployeesOnDep() {
        int departmentId = authService.getCurrentUser().getCurrentLoggedAgent().getDepartment().getId();

        List<Agent> rankingAgentsByPayment = statisticsService.getRankingAgentsByTotalPayments(departmentId);
        for(Agent agent: rankingAgentsByPayment) {
            System.out.println(agent);
        }

        System.out.println("-".repeat(100));
    }

    private void totalPaymentsOnDepartment() {
        int departmentId = authService.getCurrentUser().getCurrentLoggedAgent().getDepartment().getId();
        int nbrPayments = statisticsService.getTotalPaymentsOfDepartment(departmentId);
        if(nbrPayments > 0) {
            System.out.println("Number of total payment is: " + nbrPayments);
        } else {
            System.out.println("No payment effected yet");
        }
    }

    private void averagePaymentsInDep() {
        int departmentId = authService.getCurrentUser().getCurrentLoggedAgent().getDepartment().getId();
        System.out.println("Avg: " + statisticsService.getAveragePaymentOfDepartment(departmentId));
    }

    // Helper method for choosing an employee
    private int chooseEmployee(List<Agent> employees) {
        menuService.displayHeader("-- Choose an employee --");
        employees = employees.stream().filter(Agent::getIsActive).toList();
        List<MenuItem> items = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            Agent emp = employees.get(i);
            items.add(new MenuItem(i + 1, emp.getFirstName() + " " + emp.getLastName() + " (id=" + emp.getId() + ")"));
        }
        int choice = menuService.displayMenuAndGetChoice("Select employee", items);
        if (choice < 1 || choice > employees.size()) {
            menuService.showError("Invalid selection.");
            return -1;
        }
        System.out.println(employees.get(choice - 1));
        return employees.get(choice - 1).getId();
    }
}
