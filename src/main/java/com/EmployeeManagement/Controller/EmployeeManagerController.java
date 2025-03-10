package com.EmployeeManagement.Controller;

import com.EmployeeManagement.Entity.DepartmentEntity;
import com.EmployeeManagement.Entity.EmployeeEntity;
import com.EmployeeManagement.Entity.ProjectEntity;
import com.EmployeeManagement.Entity.SalaryEntity;
import com.EmployeeManagement.Exceptions.EmplyoeeNotFoundException;
import com.EmployeeManagement.Exceptions.PageDto;
import com.EmployeeManagement.Exceptions.ResponseDto;
import com.EmployeeManagement.Exceptions.SalaryCalculationInterface;
import com.EmployeeManagement.Service.Department.DepartmentService;
import com.EmployeeManagement.Service.Employee.EmployeeInterface;
import com.EmployeeManagement.Service.Employee.EmployeeService;
import com.EmployeeManagement.Service.Project.ProjectService;
import com.EmployeeManagement.Service.Salary.SalaryInterface;
import com.EmployeeManagement.Service.Salary.SalaryService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmployeeManagerController implements SalaryInterface {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/addAll")
    public List<EmployeeEntity> addAll(@Valid @RequestBody List<EmployeeEntity> employeeEntityList) {
        ArrayList<EmployeeEntity> savedEmployees = new ArrayList<>();
        for (EmployeeEntity employeeEntity : employeeEntityList) {
            addgeneral(employeeEntity);
        }
        return employeeEntityList;
    }

    @PostMapping("/addEmployee")
    @Transactional
    public EmployeeEntity addgeneral(@Valid @RequestBody EmployeeEntity employeeEntity) {
        DepartmentEntity departmentEntity = departmentService.findOrCreateDepartment(employeeEntity.getDepartmentEntity());
        employeeEntity.setDepartmentEntity(departmentEntity);

        SalaryEntity salaryEntity = employeeEntity.getSalaryEntity();
        salaryEntity.setEmployeeEntity(employeeEntity);

        List<ProjectEntity> projectEntities = projectService.findOrCreateProjects(employeeEntity.getProjectsEntity());
        employeeEntity.setProjectsEntity(projectEntities);

        return employeeService.save(employeeEntity);
    }

    @PostMapping("/getAll")
    public Page<EmployeeEntity> getall(@RequestBody PageDto pageDto) {
        return employeeService.findall(pageDto);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ResponseDto> gebUsingId(@PathVariable int id) throws Exception {
        return employeeService.findById(id);
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<ResponseDto> getUsingName(@PathVariable String name) throws EmplyoeeNotFoundException {
        String decodedName = URLDecoder.decode(name, StandardCharsets.UTF_8);
        return new ResponseEntity<>(new ResponseDto((employeeService.findByName(decodedName).getBody().getEmployeeEntityList()), "Employee Found Successfully", HttpStatus.OK), HttpStatus.OK);
    }


    @PatchMapping("/updateEmployeeById/{id}")
    public ResponseEntity<ResponseDto> updateEmployeeUsingId(@PathVariable int id, @Valid @RequestBody EmployeeEntity employeeEntity) {
        EmployeeEntity updatedEmployee = employeeService.update(id, employeeEntity);
        return new ResponseEntity<>(new ResponseDto(updatedEmployee, "Employee updated successfully", HttpStatus.OK), HttpStatus.OK);
    }


    @PatchMapping("/updateDepartmentById/{id}")
    @Transactional
    public ResponseEntity<ResponseDto> updateDepartmentUsingId(@PathVariable int id, @Valid @RequestBody DepartmentEntity departmentEntity) throws Exception {
        EmployeeEntity employeeEntity = employeeService.findById(id).getBody().getEmployeeEntity();
        DepartmentEntity toBeAddedDeptEntity = departmentService.findOrCreateDepartment(departmentEntity);
        employeeEntity.setDepartmentEntity(toBeAddedDeptEntity);
        return new ResponseEntity<>(new ResponseDto(employeeService.save(employeeEntity), "Department updated successfully", HttpStatus.OK), HttpStatus.OK);
    }

    @PatchMapping("/addProjectById/{id}")
    @Transactional
    public ResponseEntity<ResponseDto> addProjectById(@PathVariable int id, @Valid @RequestBody List<ProjectEntity> projectEntity) throws Exception {
        EmployeeEntity employeeEntity = employeeService.findById(id).getBody().getEmployeeEntity();
        List<ProjectEntity> toBeAddedProjectEntity = projectService.findOrCreateProjects(projectEntity);
        employeeEntity.getProjectsEntity().addAll(toBeAddedProjectEntity);
        return new ResponseEntity<>(new ResponseDto(employeeService.save(employeeEntity), "Project added successfully", HttpStatus.OK), HttpStatus.OK);
    }

    @PatchMapping("/removeProjectById/{id}")
    @Transactional
    public ResponseEntity<ResponseDto> removeProjectById(@PathVariable int id, @Valid @RequestBody List<ProjectEntity> projectEntity) throws Exception {
        EmployeeEntity employeeEntity = employeeService.findById(id).getBody().getEmployeeEntity();

        List<ProjectEntity> projectsToRemove = projectService.findOrCreateProjects(projectEntity);
        List<ProjectEntity> existingProjects = employeeEntity.getProjectsEntity();

        for (int i = 0; i < existingProjects.size(); i++) {
            for (ProjectEntity toRemove : projectsToRemove) {
                if (toRemove.getProjectName().equalsIgnoreCase(existingProjects.get(i).getProjectName())) {
                    existingProjects.remove(i);
                    i--;
                    break;
                }
            }
        }
        return new ResponseEntity<>(new ResponseDto(employeeService.save(employeeEntity), "Project removed updated successfully", HttpStatus.OK), HttpStatus.OK);
    }

    @PatchMapping("/updateSalaryById/{id}")
    @Transactional
    public ResponseEntity<ResponseDto> updateSalaryById(@PathVariable int id, @Valid @RequestBody SalaryEntity salaryEntity) throws Exception {
        EmployeeEntity employeeEntity = employeeService.findById(id).getBody().getEmployeeEntity();

        SalaryEntity existingSalary = employeeEntity.getSalaryEntity();
        existingSalary.setBasicSalary(salaryEntity.getBasicSalary());
        existingSalary.setHra(salaryEntity.getHra());
        existingSalary.setBonus(salaryEntity.getBonus());

        return new ResponseEntity<>(new ResponseDto(employeeService.save(employeeEntity), "Salary updated successfully", HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    @Transactional
    public ResponseEntity<ResponseDto> removeEmployeeById(@PathVariable int id) throws Exception {
        ResponseEntity<ResponseDto> employeeEntity = employeeService.findById(id);
        employeeService.delete(employeeEntity.getBody().getEmployeeEntity());
        return new ResponseEntity<>(new ResponseDto("Employee successfully deleted", HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/getAllManagers")
    public ResponseEntity<ResponseDto> findAllManagers() {
        return new ResponseEntity<>(new ResponseDto(employeeService.findAllManagers(), "List Of the managers", HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/getEmployessGt/{salary}")
    public ResponseEntity<ResponseDto> findAllEmployeesGt10000(@PathVariable int salary) {
        return new ResponseEntity<>(new ResponseDto(employeeService.findAllSalaryEntity_BasicSalaryGreaterThan(salary), "List of Employees whose salary is greater than:" + salary, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/getSalaryOfEmployee/{id}")
    public ResponseEntity<ResponseDto> getSalary(@PathVariable int id) throws Exception {
        EmployeeEntity employeeEntity = employeeService.findById(id).getBody().getEmployeeEntity();
        SalaryEntity salaryEntity = employeeEntity.getSalaryEntity();

        SalaryCalculationInterface calculateSalary = (salaryEntity1) -> salaryEntity1.getBonus() + salaryEntity1.getBasicSalary() + salaryEntity1.getHra();
        double finalSalary = calculateSalary.calculate(salaryEntity);
        return new ResponseEntity<>(new ResponseDto(employeeEntity, "The salary of the Employee is:" + finalSalary, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/filterDepartment/{departName}")
    public List<EmployeeEntity> filterDepartment(@PathVariable String departName) {
        String decodedName = URLDecoder.decode(departName, StandardCharsets.UTF_8);
        List<EmployeeEntity> employees = employeeService.findall().stream().filter((employeeEntity -> decodedName.equals(employeeEntity.getDepartmentEntity().getDepartmentName()))).collect(Collectors.toList());
        return employees;
    }

}