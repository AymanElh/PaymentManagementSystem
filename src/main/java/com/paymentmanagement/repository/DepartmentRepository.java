package com.paymentmanagement.repository;

import com.paymentmanagement.dao.DepartmentDAO;
import com.paymentmanagement.model.Department;

import java.util.List;

public class DepartmentRepository {
    private final DepartmentDAO departmentDAO;

    public DepartmentRepository(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    public Department getDepartmentById(int id) {
        return departmentDAO.findById(id);
    }

    public Department getDepartmentByName(String name) {
        return departmentDAO.findByName(name);
    }

    public List<Department> getAllDepartments() {
        return departmentDAO.findAll();
    }

    public Department createDepartment(Department dep) {
        return departmentDAO.save(dep);
    }

    public Department updateDepartment(Department dep ) {
        return departmentDAO.update(dep);
    }

    public boolean deleteDepartment(int id) {
        return departmentDAO.delete(id);
    }
}
