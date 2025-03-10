package com.EmployeeManagement.Service.Department;

import com.EmployeeManagement.Entity.DepartmentEntity;

public interface DepartmentInterface {
    DepartmentEntity findOrCreateDepartment (DepartmentEntity departmentEntity);
    void save(DepartmentEntity departmentEntity);
}
