package com.EmployeeManagement.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Name must contain only letters and spaces")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;


    @NotBlank(message = "Address cannot be empty")
    @Size(min = 5, max = 200, message = "Address must be between 5 and 200 characters")
    @Pattern(regexp = "^[A-Za-z0-9\\s,.-]+$", message = "Address can only contain letters, numbers, spaces, commas, periods, and hyphens")
    private String address;

    @Pattern(regexp = "^[MF]$", message = "Gender must be 'M' or 'F'")
    private String gender;

    @NotBlank(message = "Occupation cannot be empty")
    @Pattern(regexp = "(?i)^(manager|employee)$", message = "Occupation must be Manager or Employee")
    private String occupation;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "department_id")
    @Valid
    private DepartmentEntity departmentEntity;

    @OneToOne(mappedBy = "employeeEntity", cascade = CascadeType.ALL)
    @JsonManagedReference("employee-salary")
    @Valid
    private SalaryEntity salaryEntity;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @Valid
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<ProjectEntity> projectsEntity = new ArrayList<>();

}
