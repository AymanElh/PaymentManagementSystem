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
                new MenuItem(3, "Find payment"),
                new MenuItem(4, "Sort payments by amount desc"),
                new MenuItem(5, "Filter payment by type"),
                new MenuItem(6, "Get max and min payment")
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
                viewPaymentHistory();
                break;
            case 2:
                sumOfPayments();
                break;
            case 3:
                findPaymentById();
                break;
            case 4:
                sortPayments();
                break;
            case 5:
                filterPaymentsByType();
                break;
            case 6:
                getMaxMinPayment();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void viewPaymentHistory() {
        try {
            int currentAgentId = authService.getCurrentUser().getCurrentLoggedAgent().getId();
            List<Payment> payments = paymentService.getPaymentsByAgent(currentAgentId);

            if (payments.isEmpty()) {
                System.out.println("\nNo payment history found.");
                return;
            }

            System.out.println("\n=== Your Payment History ===");
            System.out.printf("%-5s %-12s %-10s %-12s %-15s%n",
                    "ID", "Type", "Amount", "Date", "Status");
            System.out.println("-----------------------------------------------------------");

            for (Payment payment : payments) {
                String paymentType = payment.getPaymentType() != null ?
                        payment.getPaymentType().toString() : "N/A";
                String status = payment.isConditionValidation() ? "Validated" : "Pending";
                String dateStr = payment.getPaymentDate() != null ?
                        payment.getPaymentDate().toString() : "N/A";

                System.out.printf("%-5d %-12s $%-9.2f %-12s %-15s%n",
                        payment.getId(),
                        paymentType,
                        payment.getAmount(),
                        dateStr,
                        status);
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();

        } catch (Exception e) {
            System.err.println("Error retrieving payment history: " + e.getMessage());
        }
    }

    private void findPaymentById() {
        int paymentId = menuService.readInt("Enter payment id");
        System.out.println("Payment with this " + paymentId + " is: ");
        System.out.println("\t " + paymentService.findPaymentById(paymentId));
    }

    private void sortPayments() {
        int currentAgentId = authService.getCurrentUser().getCurrentLoggedAgent().getId();
        List<Payment> payments = paymentService.sortPaymentsDESC(currentAgentId);
        for (Payment payment : payments) {
            System.out.println(payment);
        }
    }

    private void filterPaymentsByType() {
        int currentUserId = authService.getCurrentUser().getCurrentLoggedAgent().getId();
        List<MenuItem> typeItems = Arrays.asList(
                new MenuItem(1, "Salary"),
                new MenuItem(2, "Prime"),
                new MenuItem(3, "Bonus")
        );

        int choice = menuService.displayMenuAndGetChoice("Choose payment type", typeItems);
        String paymentType = "";
        switch (choice) {
            case 1:
                paymentType = "SALARY";
                break;
            case 2:
                paymentType = "PRIME";
                break;
            case 3:
                paymentType = "BONUS";
                break;
            default:
                System.out.println("Invalid choice. Returning to menu.");
                return;
        }

        List<Payment> filteredPayments = paymentService.filterPaymentsByType(currentUserId, paymentType);
        if (filteredPayments.isEmpty()) {
            System.out.println("\nNo payments found for type: " + paymentType);
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        System.out.println("\n=== " + paymentType + " Payments ===");
        System.out.printf("%-5s %-12s %-10s %-12s %-15s%n",
                "ID", "Type", "Amount", "Date", "Status");
        System.out.println("-----------------------------------------------------------");

        double totalAmount = 0.0;
        for (Payment payment : filteredPayments) {
            String status = payment.isConditionValidation() ? "Validated" : "Pending";
            String dateStr = payment.getPaymentDate() != null ?
                    payment.getPaymentDate().toString() : "N/A";

            System.out.printf("%-5d %-12s $%-9.2f %-12s %-15s%n",
                    payment.getId(),
                    payment.getPaymentType().toString(),
                    payment.getAmount(),
                    dateStr,
                    status);

            totalAmount += payment.getAmount();
        }

        System.out.println("-----------------------------------------------------------");
        System.out.printf("Total %s payments: $%.2f%n", paymentType, totalAmount);
        System.out.printf("Number of %s payments: %d%n", paymentType, filteredPayments.size());

        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void getMaxMinPayment() {
        try {
            int currentUserId = authService.getCurrentUser().getCurrentLoggedAgent().getId();
            Payment[] topPayments = paymentService.getMinMaxPayment(currentUserId);

            if (topPayments == null || topPayments.length < 2) {
                System.out.println("\nNo payments found to compare.");
                System.out.println("Press Enter to continue...");
                scanner.nextLine();
                return;
            }

            Payment minPayment = topPayments[0]; // index 0 is min
            Payment maxPayment = topPayments[1]; // index 1 is max

            if (minPayment == null && maxPayment == null) {
                System.out.println("\nNo payments found.");
                System.out.println("Press Enter to continue...");
                scanner.nextLine();
                return;
            }

            System.out.println("\n=== Your Minimum and Maximum Payments ===");
            System.out.printf("%-10s %-5s %-12s %-10s %-12s %-15s%n",
                    "Type", "ID", "Payment Type", "Amount", "Date", "Status");
            System.out.println("------------------------------------------------------------------------");

            if (minPayment != null) {
                String minType = minPayment.getPaymentType() != null ?
                        minPayment.getPaymentType().toString() : "N/A";
                String minStatus = minPayment.isConditionValidation() ? "Validated" : "Pending";
                String minDateStr = minPayment.getPaymentDate() != null ?
                        minPayment.getPaymentDate().toString() : "N/A";

                System.out.printf("%-10s %-5d %-12s $%-9.2f %-12s %-15s%n",
                        "MINIMUM",
                        minPayment.getId(),
                        minType,
                        minPayment.getAmount(),
                        minDateStr,
                        minStatus);
            }

            if (maxPayment != null) {
                String maxType = maxPayment.getPaymentType() != null ?
                        maxPayment.getPaymentType().toString() : "N/A";
                String maxStatus = maxPayment.isConditionValidation() ? "Validated" : "Pending";
                String maxDateStr = maxPayment.getPaymentDate() != null ?
                        maxPayment.getPaymentDate().toString() : "N/A";

                System.out.printf("%-10s %-5d %-12s $%-9.2f %-12s %-15s%n",
                        "MAXIMUM",
                        maxPayment.getId(),
                        maxType,
                        maxPayment.getAmount(),
                        maxDateStr,
                        maxStatus);
            }

            System.out.println("------------------------------------------------------------------------");


            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();

        } catch (Exception e) {
            System.err.println("Error retrieving min/max payments: " + e.getMessage());
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
        }
    }

    private void sumOfPayments() {
        int currentAgentId = authService.getCurrentUser().getCurrentLoggedAgent().getId();
        System.out.println("You total payments is: $" + paymentService.calculateTotalOfPayments(currentAgentId));
    }

}
