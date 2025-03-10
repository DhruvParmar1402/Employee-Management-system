package com.EmployeeManagement.Service.Employee;

import com.EmployeeManagement.EmpRepo.EmployeeRepo;
import com.EmployeeManagement.Entity.EmployeeEntity;
import com.EmployeeManagement.Exceptions.EmplyoeeNotFoundException;
import com.EmployeeManagement.Exceptions.PageDto;
import com.EmployeeManagement.Exceptions.ResponseDto;
import com.EmployeeManagement.Service.Department.DepartmentService;
import com.EmployeeManagement.Service.Project.ProjectService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements EmployeeInterface {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ProjectService projectService;

    @Transactional
    public EmployeeEntity save(EmployeeEntity employeeEntity) {
        return employeeRepo.save(employeeEntity);
    }

    public Page<EmployeeEntity> findall(PageDto pageDto) {
        Sort sort = "desc".equalsIgnoreCase(pageDto.getOrder()) ? Sort.by("name").descending() : Sort.by("name");
        PageRequest pageRequest = PageRequest.of(pageDto.getOffset(), pageDto.getSize(), sort);
        return employeeRepo.findAll(pageRequest);
    }

    public ResponseEntity<ResponseDto> findById(int id) throws Exception {
        Optional<EmployeeEntity> employeeEntity = employeeRepo.findById(id);
        if (employeeEntity.isEmpty()) {
            throw new EmplyoeeNotFoundException("Employee with the provided id does not exists");
        }
        return new ResponseEntity<>(new ResponseDto(employeeEntity.get(), "User found successfully", HttpStatus.OK), HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto> findByName(String name) throws EmplyoeeNotFoundException {
        List<EmployeeEntity> employeeEntity = employeeRepo.findAllByNameIgnoreCase(name);
        if (employeeEntity.isEmpty()) {
            throw new EmplyoeeNotFoundException("Employee with the provided name does not exists");
        }
        return new ResponseEntity<>(new ResponseDto(employeeEntity, "User found successfully", HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional
    public void delete(EmployeeEntity employeeEntity) {
        employeeRepo.delete(employeeEntity);
    }

    @Transactional
    public EmployeeEntity update(int id, EmployeeEntity newEmployeeEntity) {
        EmployeeEntity toBeUpdatedEmployee = employeeRepo.findById(id).get();
        toBeUpdatedEmployee.setName(newEmployeeEntity.getName());
        toBeUpdatedEmployee.setEmail(newEmployeeEntity.getEmail());
        toBeUpdatedEmployee.setAddress(newEmployeeEntity.getAddress());
        toBeUpdatedEmployee.setGender(newEmployeeEntity.getGender());
        toBeUpdatedEmployee.setOccupation(newEmployeeEntity.getOccupation());

        return employeeRepo.save(toBeUpdatedEmployee);
    }

    public List<EmployeeEntity> findAllManagers() {
        return employeeRepo.findByOccupationIgnoreCase("manager");
    }

    public List<EmployeeEntity> findAllSalaryEntity_BasicSalaryGreaterThan(int salary) {
        return employeeRepo.findBySalaryEntity_BasicSalaryGreaterThan(salary);
    }

    @Override
    public List<EmployeeEntity> findall() {
        return employeeRepo.findAll();
    }

}
