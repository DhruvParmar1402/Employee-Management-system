package com.EmployeeManagement.EmpRepo;

import com.EmployeeManagement.Entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeEntity,Integer> {
    Optional<EmployeeEntity> findByNameIgnoreCase(String name);

    List<EmployeeEntity> findAllByNameIgnoreCase(String name);

    List<EmployeeEntity> findByOccupationIgnoreCase(String manager);

    List<EmployeeEntity> findBySalaryEntity_BasicSalaryGreaterThan(int i);
}
