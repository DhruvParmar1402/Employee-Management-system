package com.EmployeeManagement.Service.Employee;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.EmployeeManagement.Entity.EmployeeEntity;
import com.EmployeeManagement.Exceptions.PageDto;
import com.EmployeeManagement.Exceptions.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeInterface {
    Page<EmployeeEntity> findall(PageDto pageDto);

    ResponseEntity<ResponseDto> findById(int id) throws Exception;

    ResponseEntity<ResponseDto> findByName(String name) throws Exception;

    void delete(EmployeeEntity employeeEntity);

    EmployeeEntity update(int id, EmployeeEntity newEmployeeEntity);

    List<EmployeeEntity> findAllManagers();

    List<EmployeeEntity> findAllSalaryEntity_BasicSalaryGreaterThan(int salary);

    List<EmployeeEntity> findall();
}
