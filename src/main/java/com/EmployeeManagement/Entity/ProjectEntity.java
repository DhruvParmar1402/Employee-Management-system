package com.EmployeeManagement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectEntity {

    @Id
    @NotBlank(message = "Project name cannot be empty")
    @Size(min = 3, max = 100, message = "Project name must be between 3 and 100 characters")
    @Pattern(regexp = "^[A-Za-z0-9 _.-]+$", message = "Project name can only contain letters, numbers, spaces, hyphens (-), underscores (_), and periods (.)")
    private String projectName;

    @NotBlank(message = "Project description cannot be empty")
    @Size(min = 3, max = 200, message = "Project description must be between 3 and 200 characters")
    @Pattern(regexp = "^[A-Za-z0-9 _.-]+$", message = "Project description can only contain letters, numbers, spaces, hyphens (-), underscores (_), and periods (.)")
    private String description;

    @NotNull(message = "Project start date cannot be null")
    @FutureOrPresent(message = "Project start date must be today or in the future")
    private LocalDate startDate;

    @ManyToMany(mappedBy = "projectsEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EmployeeEntity> employeeEntity = new ArrayList<>();
}
