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

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("No rows affected, Cannot update payment");
            }
            return payment;
        } catch (SQLException e) {
            System.err.print("Database error while saving payment: " + e.getMessage());
            throw new RuntimeException("Failed to save payment on database");
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
        try (Connection conn = dbConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM payments");
            ResultSet rs = stmt.executeQuery();
            return payments;
        } catch (SQLException e) {
            System.err.println("Database error while get all payments");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Payment findById(int id) {
        return null;
    }


}
