package com.EmployeeManagement.Exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class ExceptionsDto {
    private String field;
    private String message;
    private LocalDateTime dateTime;
    private HttpStatus httpStatus;

    public ExceptionsDto(String field, String message, HttpStatus code) {
        this.field = field;
        this.message = message;
        this.httpStatus=code;
        dateTime=LocalDateTime.now();
    }
}
