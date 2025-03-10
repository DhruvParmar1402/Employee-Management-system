package com.EmployeeManagement.EmpRepo;

import com.EmployeeManagement.Entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepo extends JpaRepository<ProjectEntity, String> {
}
