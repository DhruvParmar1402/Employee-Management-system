package com.EmployeeManagement.Exceptions;

import com.EmployeeManagement.Entity.EmployeeEntity;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResponseDto {
    private List<EmployeeEntity> employeeEntityList;
    private EmployeeEntity employeeEntity;
    private String message;
    private HttpStatus statusCode;
    private LocalDateTime dateTime;

    public ResponseDto(EmployeeEntity employeeEntity, String message, HttpStatus statusCode) {
        this.employeeEntity = employeeEntity;
        this.message = message;
        this.statusCode = statusCode;
        dateTime = LocalDateTime.now();
    }

    public ResponseDto(List<EmployeeEntity> employeeEntityList, String message, HttpStatus statusCode) {
        this.employeeEntityList = employeeEntityList;
        this.message = message;
        this.statusCode = statusCode;
        dateTime = LocalDateTime.now();
    }

    public ResponseDto(String employeeSuccessfullyDeleted, HttpStatus httpStatus) {
        this.message = employeeSuccessfullyDeleted;
        this.statusCode = httpStatus;
    }
}
