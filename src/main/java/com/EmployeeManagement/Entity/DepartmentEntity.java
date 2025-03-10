

package com.EmployeeManagement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DepartmentEntity {

    @Id
    @Column(name = "departmentName")
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Name must contain only letters and spaces")
    private String departmentName;

    @PositiveOrZero(message = "Number of employees cannot be 0 or negative")
    @Column(name = "numberOfEmployees")
    private int numberOfEmployees;

    @OneToMany(mappedBy = "departmentEntity")
    @JsonIgnore
    private List<EmployeeEntity> employeeEntity=new ArrayList<>();
}
