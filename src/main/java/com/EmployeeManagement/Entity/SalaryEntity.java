package com.EmployeeManagement.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SalaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "basicSalary")
    @NotNull(message = "Basic salary cannot be null")
    @Min(value = 1, message = "Basic salary must be at least 1")
    private double basicSalary;


    @Column(name = "hra")
    @NotNull(message = "HRA cannot be null")
    @Min(value = 1, message = "HRA must be at least 1")
    private double hra;

    @Column(name = "bonus")
    @NotNull(message = "Bonus cannot be null")
    @Min(value = 1, message = "Bonus must be a positive value")

    private double bonus;

    @OneToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference("employee-salary")
    private EmployeeEntity employeeEntity;
}
