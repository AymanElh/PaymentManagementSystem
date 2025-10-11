package com.paymentmanagement;

import com.paymentmanagement.config.DatabaseConnection;
import com.paymentmanagement.dao.*;
import com.paymentmanagement.repository.AgentRepository;
import com.paymentmanagement.repository.*;
import com.paymentmanagement.service.*;
import com.paymentmanagement.view.ConsoleView;
import com.paymentmanagement.view.MenuService;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        Scanner scanner = new Scanner(System.in);


        // Dao
        AgentDAO agentDAO = new AgentDAOImp(dbConnection);
        DepartmentDAO departmentDAO = new DepartmentDAO(dbConnection);
        PaymentDAOImp paymentDAOImp = new PaymentDAOImp(dbConnection);

        // Repositories
        AgentRepository agentRepository = new AgentRepository(agentDAO);
        DepartmentRepository departmentRepository = new DepartmentRepository(departmentDAO);
        PaymentRepository paymentRepository = new PaymentRepository(paymentDAOImp);

        // Services
        AgentService agentService = new AgentServiceImp(agentRepository);
        DepartmentService departmentService = new DepartmentServiceImp(departmentRepository, agentRepository);
        PaymentServiceImp paymentService = new PaymentServiceImp(paymentRepository);
        AuthService authService = new AuthServiceImp(agentRepository, null);
        MenuService menuService = new MenuService(scanner);
        StatisticsService statisticsService = new StatisticsServiceImp(paymentRepository, agentRepository, departmentRepository);

        ConsoleView view = new ConsoleView(authService, menuService, scanner, agentService, authService, paymentService, departmentService, statisticsService);
//        view.show();

        System.out.println(paymentService.getPaymentsByMonths(19294, 10));
    }
}