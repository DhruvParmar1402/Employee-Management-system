package com.EmployeeManagement.Exceptions;

import com.EmployeeManagement.Entity.SalaryEntity;

@FunctionalInterface
public interface SalaryCalculationInterface {
    double calculate(SalaryEntity salaryEntity);
}
