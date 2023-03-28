package com.thoughtworks.springbootemployee.exception;

public class EmployeeNotFoundException extends RuntimeException {
    private Long employeeId;

    public EmployeeNotFoundException(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String getMessage() {
        return "Not found employee which id is " + employeeId;
    }
}
