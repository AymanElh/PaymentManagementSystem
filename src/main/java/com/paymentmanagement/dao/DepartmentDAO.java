package com.paymentmanagement.dao;

import com.paymentmanagement.config.DatabaseConnection;
import com.paymentmanagement.model.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO implements GenericDAO<Department>{
    private final DatabaseConnection dbConnection;

    public DepartmentDAO(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Department save(Department department) {
        try (Connection conn = dbConnection.getConnection()) {
            String departmentQuery = "INSERT INTO departments (id, name, description) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(departmentQuery);
            stmt.setInt(1, department.getId());
            stmt.setString(2, department.getName());
            stmt.setString(3, department.getDescription());
            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected == 0) {
                throw new RuntimeException("Cannot insert new department");
            }

            return department;
        } catch (SQLIntegrityConstraintViolationException dup) {
            // Unique constraint violation (e.g., duplicate department name)
            System.err.println("Department already exists: " + department.getName());
            return null; // signal duplicate to upper layers instead of crashing
        } catch (SQLException e) {
            System.err.println("Database error while saving department: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection conn = dbConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM departments WHERE id = ?");
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected == 0) {
                throw new RuntimeException("Department not deleted, no rows affected");
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Database error while deleting a department: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Department update(Department department) {
        try (Connection conn = dbConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE departmetns SET name = ? , description = ? WHERE id = ?");
            if(stmt.executeUpdate() == 0) {
                throw new RuntimeException("Error updating record, no rows affected");
            }

            return department;
        } catch (SQLException e) {
            System.err.println("Database error while updating department: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM departments");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                departments.add(convertResultIntoDepartment(rs));
            }
            return departments;
        } catch (SQLException e) {
            System.err.println("Database error while getting departments: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Department findById(int id) {
        try(Connection conn = dbConnection.getConnection();) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM departments WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Department department = null;
            if(rs.next()) {
                return convertResultIntoDepartment(rs);
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Database error while getting department by id: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Department findByName(String name) {
        try(Connection conn = dbConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM departments WHERE name = ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return convertResultIntoDepartment(rs);
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Database error while getting department by name: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Department convertResultIntoDepartment(ResultSet resultSet) throws SQLException {
        return new Department(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"));
    }
}
