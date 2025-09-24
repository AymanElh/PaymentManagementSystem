package com.paymentmanagement.dao;

import com.paymentmanagement.model.Agent;
import com.paymentmanagement.config.DatabaseConnection;
import com.paymentmanagement.model.AgentType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgentDAOImp implements AgentDAO{
    private final DatabaseConnection dbConnection;

    public AgentDAOImp(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Agent save(Agent agent) {
        Connection connection = null;
        PreparedStatement userStmt = null;
        PreparedStatement agentStmt = null;

        try {
            connection = dbConnection.getConnection();

            // Start transaction
            connection.setAutoCommit(false);

            String userQuery = "INSERT INTO users (id, first_name, last_name, email, password, phone) VALUES (?, ?, ?, ?, ?, ?)";
            userStmt = connection.prepareStatement(userQuery);
            userStmt.setInt(1, agent.getUserId());
            userStmt.setString(2, agent.getFirstName());
            userStmt.setString(3, agent.getLastName());
            userStmt.setString(4, agent.getEmail());
            userStmt.setString(5, agent.getPassword());
            userStmt.setString(6, agent.getPhone());
            int userRowsAffected = userStmt.executeUpdate();
            System.out.println(agent.getId());
            System.out.println(agent);
            if (userRowsAffected == 0) {
                throw new SQLException("User creation failed. no rows affected");
            }

            String agentQuery = "INSERT INTO agents (id, user_id, type, department_id, start_date, is_active) VALUES (?, ?, ?, ?, ?, ?)";
            agentStmt = connection.prepareStatement(agentQuery);
            agentStmt.setInt(1, agent.getId());
            agentStmt.setInt(2, agent.getUserId());
            agentStmt.setString(3, agent.getAgentType().name().toLowerCase());
            agentStmt.setObject(4, agent.getDepartment() != null ? agent.getDepartment().getId() : null);
            agentStmt.setString(5, agent.getStartDateAsString());
            agentStmt.setBoolean(6, agent.getIsActive());
            int agentRowsAffected = agentStmt.executeUpdate();
            if (agentRowsAffected == 0) {
                throw new SQLException("User creation failed. no rows affected");
            }

            connection.commit();

            return agent;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("sql exception: " + e.getMessage());
            return null;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Agent update(Agent agent) {
        Connection connection = null;
        PreparedStatement userStmt = null;
        PreparedStatement agentStmt = null;
        try {
            connection = dbConnection.getConnection();

            String userQuery = "UPDATE users SET first_name = ? , last_name = ? , email = ? , password = ? , phone = ?";
            userStmt = connection.prepareStatement(userQuery);
            userStmt.setString(1, agent.getFirstName());
            userStmt.setString(2, agent.getLastName());
            userStmt.setString(3, agent.getEmail());
            userStmt.setString(4, agent.getPassword());
            userStmt.setString(5,  agent.getPhone());

            String agentQuery = "UPDATE agents SET first_name = ? , last_name = ? , email = ? , password = ? , phone = ?";
            agentStmt = connection.prepareStatement(agentQuery);
            agentStmt.setString(1, agent.getFirstName());
            agentStmt.setString(2, agent.getLastName());
            agentStmt.setString(3, agent.getEmail());
            agentStmt.setString(4, agent.getPassword());
            agentStmt.setString(5,  agent.getPhone());

            int userRowsAffected = userStmt.executeUpdate();
            int agentRowsAffected = agentStmt.executeUpdate();

            if(userRowsAffected == 0) {
                throw new SQLException("Agent is not created on database");
            }
            if(agentRowsAffected == 0) {
                throw new SQLException("Agent is not created on database");
            }

            return agent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("DELETE  FROM users WHERE id = ?");
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public List<Agent> findAll() {
        List<Agent> agents = new ArrayList<>();
        String query = """
                    SELECT u.id as user_id, u.first_name, u.last_name, u.email, u.phone,
                           a.id, a.type, a.department_id, a.start_date, a.is_active
                    FROM users u
                    JOIN agents a ON a.user_id = u.id
                """;
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                agents.add(convertResultToAgent(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agents;
    }

    @Override
    public Agent findById(int id) {
        String query = """
                    SELECT a.id, u.id as user_id, u.first_name, u.last_name, u.email, u.phone, 
                    a.type, a.department_id, a.start_date, a.is_active
                    FROM users u
                    JOIN agents a ON a.user_id = u.id
                    WHERE a.id = ?
                    LIMIT 1;
                """;
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            Agent agent = convertResultToAgent(rs);
            return agent;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Agent findByEmail(String email) {
        String query = """
                    SELECT a.id, u.id as user_id, u.first_name, u.last_name, u.email, u.phone, 
                    a.type, a.department_id, a.start_date, a.is_active
                    FROM users u
                    JOIN agents a ON a.user_id = u.id
                    WHERE a.id = ?
                    LIMIT 1;
                """;
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            Agent agent = convertResultToAgent(rs);
            return agent;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Agent> findByDeparment(String department) {
        List<Agent> agentsByDep = new ArrayList<>();
        String query = """
                    SELECT a.id, u.id as user_id, u.first_name, u.last_name, u.email, u.phone, 
                    a.type, a.department_id, a.start_date, a.is_active
                    FROM users u
                    JOIN agents a ON a.user_id = u.id
                    WHERE a.department_id = ?
                    LIMIT 1;
                """;
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, department);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                agentsByDep.add(convertResultToAgent(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return agentsByDep;
    }

    // helper method for converting data from a database into Agent object
    private Agent convertResultToAgent(ResultSet rs) throws SQLException {
        Agent agent = new Agent(
                rs.getInt("user_id"),
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                "",
                rs.getString("phone"),
                AgentType.valueOf(rs.getString("type").toUpperCase()),
                rs.getBoolean("is_active"),
                rs.getDate("start_date"),
                null
        );

        return agent;
    }
}
