package com.thoughtworks.springbootemployee.model.dto;

import com.thoughtworks.springbootemployee.model.entity.Gender;

public class EmployeeResponse {
    private Long id;

    private String name;

    private int age;

    private Gender gender;

    private int Salary;

    private Long companyId;

    public EmployeeResponse() {
    }

    public EmployeeResponse(Long id, String name, int age, Gender gender, int salary, Long companyId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        Salary = salary;
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
