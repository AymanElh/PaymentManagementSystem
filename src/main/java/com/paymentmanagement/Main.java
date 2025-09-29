package com.paymentmanagement;

import com.paymentmanagement.config.DatabaseConnection;
import com.paymentmanagement.dao.AgentDAO;
import com.paymentmanagement.dao.AgentDAOImp;
import com.paymentmanagement.dao.DepartmentDAO;
import com.paymentmanagement.dao.GenericDAO;
import com.paymentmanagement.model.Agent;
import com.paymentmanagement.model.AgentType;
import com.paymentmanagement.model.Department;
import com.paymentmanagement.repository.AgentRepository;
import com.paymentmanagement.service.*;
import com.paymentmanagement.view.ConsoleView;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        // DAOs
        AgentDAO agentDAO = new AgentDAOImp(dbConnection);

        // Repositories
        AgentRepository agentRepository = new AgentRepository(agentDAO);

        // Services
        AgentService agentService = new AgentServiceImp(agentRepository);
        AuthService authService = new AuthServiceImp(agentRepository);

    }
}