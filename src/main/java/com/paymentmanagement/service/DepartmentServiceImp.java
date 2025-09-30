package com.paymentmanagement.service;

import com.paymentmanagement.model.Department;
import com.paymentmanagement.repository.AgentRepository;
import com.paymentmanagement.repository.DepartmentRepository;

import java.util.List;

public class DepartmentServiceImp implements DepartmentService{
    private final DepartmentRepository departmentRepository;
    private final AgentRepository agentRepository;

    public DepartmentServiceImp(DepartmentRepository departmentRepository, AgentRepository agentRepository) {
        this.departmentRepository = departmentRepository;
        this.agentRepository = agentRepository;
    }

    @Override
    public Department addDepartment(Department department) {
        return departmentRepository.createDepartment(department);
    }

    @Override
    public Department updateDepartment(Department department) {
        return null;
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.getAllDepartments();
    }

    @Override
    public Department deleteDepartment(int departmentId) {
        // Load department first
        Department department = departmentRepository.getDepartmentById(departmentId);
        if (department == null) {
            return null; // nothing to delete
        }

        // Check if there are any agents (manager or employees) in this department
        boolean hasManager = agentRepository.getDepartmentManager(departmentId).isPresent();
        boolean hasEmployees = !agentRepository.getEmployeesByDepartmentId(departmentId).isEmpty();
        if (hasManager || hasEmployees) {
            // Simple behavior: do not delete and return null to indicate it wasn't deleted
            return null;
        }

        boolean deleted = departmentRepository.deleteDepartment(departmentId);
        return deleted ? department : null;
    }
}
