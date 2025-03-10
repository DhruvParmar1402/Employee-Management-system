package com.EmployeeManagement.Service.Department;

import com.EmployeeManagement.EmpRepo.DepartmentRepo;
import com.EmployeeManagement.Entity.DepartmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class DepartmentService implements DepartmentInterface{

    @Autowired
    private DepartmentRepo departmentRepository;

    public DepartmentEntity findOrCreateDepartment(DepartmentEntity departmentEntity) {
        Optional<DepartmentEntity>departmentEntityOptional=departmentRepository.findById(departmentEntity.getDepartmentName());
        if(departmentEntityOptional.isPresent())
        {
            departmentEntityOptional.get().setNumberOfEmployees(departmentEntity.getNumberOfEmployees());
            return departmentEntityOptional.get();
        }
        return  departmentRepository.save(departmentEntity);
    }

    public void save(DepartmentEntity departmentEntity) {
        departmentRepository.save(departmentEntity);
    }
}
