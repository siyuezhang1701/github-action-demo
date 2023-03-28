package com.thoughtworks.springbootemployee.extension;

import com.thoughtworks.springbootemployee.model.entity.Employee;

public class EmployeeMerger {
    public static void merge(Employee source, Employee target) {
        if (source.getName() != null && !source.getName().isEmpty()) target.setName(source.getName());
        if (source.getAge() != 0) target.setAge(source.getAge());
        if (source.getGender() != null) target.setGender(source.getGender());
        if (source.getSalary() != 0) target.setSalary(source.getSalary());
    }
}
