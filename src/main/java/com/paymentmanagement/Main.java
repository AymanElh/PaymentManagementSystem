package com.paymentmanagement;

import com.paymentmanagement.config.DatabaseConnection;
import com.paymentmanagement.dao.AgentDAOImp;
import com.paymentmanagement.dao.DepartmentDAO;
import com.paymentmanagement.dao.GenericDAO;
import com.paymentmanagement.model.Agent;
import com.paymentmanagement.model.AgentType;
import com.paymentmanagement.model.Department;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        GenericDAO<Agent> agentDAO = new AgentDAOImp(dbConnection);

        Agent agent1 = new Agent("ayman", "elh", "ayman1@gmail.com", "123456", "06119292", AgentType.EMPLOYEE, true, new Date());
        Agent agent2 = new Agent("sara", "ali", "sara.ali1@gmail.com", "abcdef", "06223344", AgentType.MANAGER, true, new Date());
        Agent agent3 = new Agent("mohamed", "hassan", "mohamed.hassan@gmail.com", "pass123", "06334455", AgentType.EMPLOYEE, true, new Date());
        Agent agent4 = new Agent("omar", "said", "omar.said@gmail.com", "omarpass", "06556677", AgentType.EMPLOYEE, true, new Date());


        Agent agent5 = new Agent("lina", "mohsen", "lina.mohsen@gmail.com", "lina123", "06778899", AgentType.EMPLOYEE, true, new Date());
        Agent agent6 = new Agent("khaled", "fathy", "khaled.fathy@gmail.com", "khaledpass", "06889900", AgentType.MANAGER, true, new Date());
        Agent agent7 = new Agent("dina", "samir", "dina.samir@gmail.com", "dina456", "06990011", AgentType.EMPLOYEE, true, new Date());
        Agent agent8 = new Agent("youssef", "adel", "youssef.adel@gmail.com", "youssef789", "06112233", AgentType.EMPLOYEE, true, new Date());


        agentDAO.delete(agent5.getId());

//        System.out.println(agent1);
//        agent1.setDepartment();

        try {
            List<Agent> agents = agentDAO.findAll();
            System.out.println(agents.size());
            for(Agent agent: agents) {
                System.out.println("\t Agent: --- ");
                System.out.println(agent);
            }
        } catch (Exception e) {
            System.err.println("Error " + e.getMessage());
        }
        System.out.println("-".repeat(100));
//        System.out.println(agentDAO.findById(39151));

        // Departments crud


        Department dep1 = new Department(1, "Development", "");
        Department dep2 = new Department(2, "Artificial intelligence", "");
        Department dep3 = new Department(3, "Marketing", "");

//        agent1.setDepartment(dep1);
//        System.out.println(agent1);

//        System.out.println(departmentDAO.findById(1));
//        departmentDAO.save(dep2);
//        departmentDAO.save(dep3);
//        System.out.println(departmentDAO.findById(1));

    }
}