package com.EmployeeManagement.Service.Project;

import com.EmployeeManagement.Entity.ProjectEntity;

import java.util.List;

public interface ProjectInterface {
    List<ProjectEntity> findOrCreateProjects(List<ProjectEntity> projectEntities);
}
