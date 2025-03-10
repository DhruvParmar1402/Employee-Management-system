package com.EmployeeManagement.EmpRepo;

import com.EmployeeManagement.Entity.SalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepo extends JpaRepository<SalaryEntity, Integer> {
}
