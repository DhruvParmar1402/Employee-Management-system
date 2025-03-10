package com.EmployeeManagement.EmpRepo;

import com.EmployeeManagement.Entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<DepartmentEntity,String> {
}
