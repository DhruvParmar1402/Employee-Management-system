package com.EmployeeManagement.Service.Project;

import com.EmployeeManagement.EmpRepo.ProjectRepo;
import com.EmployeeManagement.Entity.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService implements ProjectInterface {

    @Autowired
    private ProjectRepo projectRepository;

    public List<ProjectEntity> findOrCreateProjects(List<ProjectEntity> projectEntities) {
        List<ProjectEntity> savedProjects = new ArrayList<>();
        for (ProjectEntity project : projectEntities) {
            ProjectEntity existingProject = projectRepository.findById(project.getProjectName()).orElse(null);
            if (existingProject == null) {
                existingProject = projectRepository.save(project);
            }
            savedProjects.add(existingProject);
        }
        return savedProjects;
    }
}
