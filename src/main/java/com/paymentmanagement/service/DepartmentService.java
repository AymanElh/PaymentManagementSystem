package com.paymentmanagement.service;

import com.paymentmanagement.model.Department;

import java.util.List;

public interface DepartmentService {
    Department addDepartment(Department department);
    Department updateDepartment(Department department);
    List<Department> getAllDepartments();
    Department deleteDepartment(int departmentId);
}
