package com.paymentmanagement.dao;

import com.paymentmanagement.config.DatabaseConnection;
import com.paymentmanagement.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImp implements GenericDAO<Payment>{
    private final DatabaseConnection dbConnection;

    public PaymentDAOImp(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Payment save(Payment payment) {
        try (Connection conn = dbConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO payments (type, amount, payment_date, condition_validation, agent_id) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, payment.getPaymentType().name());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getDateAsString());
            stmt.setBoolean(4, payment.isConditionValidation());
            stmt.setInt(5, payment.getAgent().getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("No rows affected, Cannot update payment");
            }
            return payment;
        } catch (SQLException e) {
//            System.err.print("Database error while saving payment: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Payment update(Payment payment) {
        try (Connection conn = dbConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE payments SET amount = ? , payment_date = ? , condition_validation = ? WHERE id = ?");
            stmt.setDouble(1, payment.getAmount());
            stmt.setString(2, payment.getDateAsString());
            stmt.setBoolean(3, payment.isConditionValidation());
            stmt.setInt(4, payment.getId());

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected == 0) {
                throw new RuntimeException("No rows affected, cannot update payment");
            }
            return payment;
        } catch (SQLException e) {
            System.err.println("Database error while updating payment: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection conn = dbConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM payments WHERE id = ?");
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected == 0) {
                throw new RuntimeException("Cannot delete payment, no rows affected on database");
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Database error while deleting payment: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Payment> findAll() {
        List<Payment> payments = new ArrayList<>();
        String query = """
            SELECT p.id, p.type, p.amount, p.payment_date, p.condition_validation,
                   a.id as agent_id, u.id as user_id, u.first_name, u.last_name,
                   u.email, u.password, u.phone, a.type as agent_type, a.start_date, a.is_active,
                   d.id as department_id, d.name as department_name, d.description as department_description
            FROM payments p
            JOIN agents a ON p.agent_id = a.id
            JOIN users u ON a.user_id = u.id
            LEFT JOIN departments d ON a.department_id = d.id
            ORDER BY p.payment_date DESC
            """;

        try (Connection conn = dbConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Payment payment = convertResultSetToPayment(rs);
                payments.add(payment);
            }

        } catch (SQLException e) {
            System.err.println("Database error while getting all payments: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return payments;
    }

    @Override
    public Payment findById(int id) {
        String query = """
            SELECT p.id, p.type, p.amount, p.payment_date, p.condition_validation,
                   a.id as agent_id, u.id as user_id, u.first_name, u.last_name,
                   u.email, u.password, u.phone, a.type as agent_type, a.start_date, a.is_active,
                   d.id as department_id, d.name as department_name, d.description as department_description
            FROM payments p
            JOIN agents a ON p.agent_id = a.id
            JOIN users u ON a.user_id = u.id
            LEFT JOIN departments d ON a.department_id = d.id
            WHERE p.id = ?
            """;

        try (Connection conn = dbConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return convertResultSetToPayment(rs);
            }

        } catch (SQLException e) {
            System.err.println("Database error while finding payment by id: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Payment> getPaymentsByAgent(int agentId) {
        List<Payment> payments = new ArrayList<>();
        String query = """
            SELECT p.id, p.type, p.amount, p.payment_date, p.condition_validation,
                   a.id as agent_id, u.id as user_id, u.first_name, u.last_name,
                   u.email, u.password, u.phone, a.type as agent_type, a.start_date, a.is_active,
                   d.id as department_id, d.name as department_name, d.description as department_description
            FROM payments p
            JOIN agents a ON p.agent_id = a.id
            JOIN users u ON a.user_id = u.id
            LEFT JOIN departments d ON a.department_id = d.id
            WHERE p.agent_id = ?
            ORDER BY p.payment_date DESC
            """;

        try (Connection conn = dbConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, agentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Payment payment = convertResultSetToPayment(rs);
                payments.add(payment);
            }

        } catch (SQLException e) {
            System.err.println("Database error while getting payments by agent: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return payments;
    }

    // Transfer the result set from the database to Payment Object
    private Payment convertResultSetToPayment(ResultSet rs) throws SQLException {
        com.paymentmanagement.model.Department department = null;
        Integer departmentId = rs.getObject("department_id", Integer.class);
        if (departmentId != null && departmentId > 0) {
            String departmentName = rs.getString("department_name");
            String departmentDescription = rs.getString("department_description");
            department = new com.paymentmanagement.model.Department(departmentId, departmentName, departmentDescription);
        }

        com.paymentmanagement.model.Agent agent = new com.paymentmanagement.model.Agent(
            rs.getInt("user_id"),
            rs.getInt("agent_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("phone"),
            com.paymentmanagement.model.AgentType.valueOf(rs.getString("agent_type").toUpperCase()),
            rs.getBoolean("is_active"),
            rs.getDate("start_date"),
            department
        );

        Payment payment = new Payment(
            agent,
            rs.getDouble("amount"),
            rs.getBoolean("condition_validation"),
            rs.getDate("payment_date"),
            com.paymentmanagement.model.PaymentType.valueOf(rs.getString("type"))
        );
        payment.setId(rs.getInt("id"));

        return payment;
    }

}
