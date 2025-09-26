package com.paymentmanagement;

import com.paymentmanagement.config.DatabaseConnection;
import com.paymentmanagement.dao.AgentDAOImp;
import com.paymentmanagement.dao.DepartmentDAO;
import com.paymentmanagement.dao.GenericDAO;
import com.paymentmanagement.model.Agent;
import com.paymentmanagement.model.AgentType;
import com.paymentmanagement.model.Department;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        DepartmentDAO departmentDAO = new DepartmentDAO(dbConnection);
        GenericDAO<Agent> agentDAO = new AgentDAOImp(dbConnection, departmentDAO);

        Agent agent1 = new Agent("ayman", "elh", "ayman@gmail.com", "123456", "06119292", AgentType.EMPLOYEE, true, new Date());
        Agent agent2 = new Agent("sara", "ali", "sara.ali@gmail.com", "abcdef", "06223344", AgentType.MANAGER, true, new Date());
        Agent agent3 = new Agent("mohamed", "hassan", "mohamed.hassan@gmail.com", "pass123", "06334455", AgentType.EMPLOYEE, true, new Date());
        Agent agent4 = new Agent("omar", "said", "omar.said@gmail.com", "omarpass", "06556677", AgentType.EMPLOYEE, true, new Date());


//        System.out.println(agent1);
//        agent1.setDepartment();

        List<Agent> agents = agentDAO.findAll();
        for(Agent agent: agents) {
            System.out.println(agent);
        }
        System.out.println("-".repeat(100));
//        System.out.println(agentDAO.findById(39151));

        // Departments crud


        Department dep1 = new Department(1, "Development", "");
        Department dep2 = new Department(2, "Artificial intelligence", "");
        Department dep3 = new Department(3, "Marketing", "");

        agent1.setDepartment(dep1);
        System.out.println(agent1);

//        departmentDAO.save(dep1);
//        departmentDAO.save(dep2);
//        departmentDAO.save(dep3);
        System.out.println(departmentDAO.findById(1));

    }
}