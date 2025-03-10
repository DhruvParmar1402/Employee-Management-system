package com.EmployeeManagement.Exceptions;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class PageDto {
    private int size;
    private int offset;
    private boolean sort;
    private String order;
}
